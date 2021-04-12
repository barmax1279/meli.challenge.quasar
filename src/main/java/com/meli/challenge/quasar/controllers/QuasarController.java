package com.meli.challenge.quasar.controllers;

import com.meli.challenge.quasar.entities.*;
import com.meli.challenge.quasar.excepciones.InsuffientDistancesArgumentException;
import com.meli.challenge.quasar.excepciones.MessageArgumentNullException;
import com.meli.challenge.quasar.helpers.MessageBuilder;
import com.meli.challenge.quasar.helpers.SatellitesManager;
import com.meli.challenge.quasar.helpers.TrilaterationHelper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class QuasarController {

    /**
     * Metodo que obtiene la ubicacion y mensaje enviado por la nave.
     *
     * @param request recibe la informacion de al menos 3 satelites con respecto a la ubicacion, distancia y mensaje recibido
     * @return http code. TopSecretResponse, contiene la uicacion de la nave, y el mensaje descifrado.
     */

    @PostMapping(value = "/topsecret", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TopSecretResponse> topSecret( @RequestBody final TopSecretRequest request)
            throws MessageArgumentNullException, InsuffientDistancesArgumentException {

        TopSecretResponse topSecretResponse = new TopSecretResponse();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            topSecretResponse = SatellitesManager.getInstance().calcPosition(request);
        }catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
            topSecretResponse.setErrorDescription(ex.getMessage());
        }

        return new ResponseEntity<>(topSecretResponse, httpStatus);
    }


    /**
     * almacena la informacion individual enviada desde cada satellite
     *
     * @param satelliteName nombre del satellite
     * @param satelliteInfo request que ontiene la informacion del satellite, distancia y el mensaje.
     * @return http code
     */
    @PostMapping(value = "/topsecret_split/{satellite_name}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TopSecretResponse> topSecretSplit( @PathVariable(value = "satellite_name") final String satelliteName,
                                                             @RequestBody final TopSecretRequest satelliteInfo) {

        TopSecretResponse topSecretResponse = new TopSecretResponse();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            SatellitesManager.getInstance().addOneSatelliteDistance(satelliteName, satelliteInfo);
        }catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
            topSecretResponse.setErrorDescription(ex.getMessage());
        }

        return new ResponseEntity<>(topSecretResponse, httpStatus);
    }

    /**
     * Calcula la informacion que fue enviada de forma parcial. Se debe oontar con 3 distancias previamente enviadas.
     *
     * @return http code. Y TopSecretResponse, con la informacion de la ubicacion y mensaje descifrado.
     */
    @GetMapping(value = "/topsecret_split")
    public ResponseEntity<TopSecretResponse> topSecretSplit( ) {

        TopSecretResponse topSecretResponse = new TopSecretResponse();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            topSecretResponse = SatellitesManager.getInstance().getLocationFromPartialRequest();
        }catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
            topSecretResponse.setErrorDescription(ex.getMessage());
        }
        return new ResponseEntity<>(topSecretResponse, httpStatus);
    }
}
