package com.meli.challenge.quasar.helpers;

import com.meli.challenge.quasar.excepciones.MessageArgumentNullException;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

public class MessageBuilder {

    /**
     * Esta clase se utiliza para descodificar y armar un unico mensaje a partir
     * de los datos que van llegando desde los distintos satelites
     *
     * @Param messages, es un array donde en cada posicion del array principal
     * recibe un array con el mensaje captado desde cada satelite
     *
     * Ej:
     * messages[0] : ["este","","","mensaje",""]
     * messages[2] : ["","es","","","secreto"]
     * messages[2] : ["este","","","mensaje",""]
     *
     * @Output String, devuelve un unico mensaje. En caso que no se puede descifrar devuelve un null
     **/

    public static String getMessage(String[] ...messages) throws MessageArgumentNullException {
        //recupero el tamaño del primer array
        final int size = messages.length;

        //comparo que todos los arrays tengan la misma longitud
        if (size == 0 ) {
            throw new MessageArgumentNullException("No se encontraron mensajes");
        }

        //verifico si en el primer array contiene algun elemento
        if (messages[0] == null || messages[0].length == 0 ) {
            throw new MessageArgumentNullException("No se encontraron mensajes");
        }

        //Comparo la cantidad de elementos del primer elemento con el resto de los arrays
        for (int i = 1; i < size; i++) {
            if (messages[i] == null || messages[0].length != messages[i].length)
                throw new MessageArgumentNullException("Inconsistencia en la cantidad de elementos");
        }

        List<String> parsedMessage = new ArrayList <String>();
        String temp;
        for (int i = 0; i < messages[0].length; i++) {
            temp = Strings.EMPTY;
            for (String[] messageSatellite : messages) {
                if (Strings.isNotBlank(messageSatellite[i])) {
                    temp = messageSatellite[i];
                }
            }
            parsedMessage.add( temp );
        }

        return String.join(" ", parsedMessage).trim();
    }
}
