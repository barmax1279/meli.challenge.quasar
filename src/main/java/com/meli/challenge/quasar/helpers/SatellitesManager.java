 package com.meli.challenge.quasar.helpers;

import com.meli.challenge.quasar.entities.*;
import com.meli.challenge.quasar.excepciones.InsuffientDistancesArgumentException;
import com.meli.challenge.quasar.excepciones.MessageArgumentNullException;
import com.meli.challenge.quasar.excepciones.MissingSatelliteException;
import com.meli.challenge.quasar.excepciones.SattelliteNotFoundException;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

 public class SatellitesManager {

    private static SatellitesManager instance;
    private ConcurrentHashMap<String, TopSecretSatelliteRequest> parcialRequests;

    private List<Satellite> satellites;

            /*

            Kenobi: [-500, -200]
            ● Skywalker: [100, -100]
            ● Sato: [500, 100]


                    {
            “name”: "kenobi",
            “distance”: 100.0,
            “message”: ["este", "", "", "mensaje", ""]
            },
            {
            “name”: "skywalker",
            “distance”: 115.5
            “message”: ["", "es", "", "", "secreto"]
            },
            {
            “name”: "sato",
            “distance”: 142.7
            “message”: ["este", "", "un", "", ""]
            }

         */

    private SatellitesManager(){
        this.parcialRequests = new ConcurrentHashMap<>();
        setDefaultSatellites();
    }

    public void setDefaultSatellites() {
        this.satellites = new ArrayList<>();
        this.satellites.add(new Satellite("kenobi", new Position(-500, -200)));
        this.satellites.add(new Satellite("Skywalker", new Position(100, -100)));
        this.satellites.add(new Satellite("Sato", new Position(500, 100)));
    }

    public void setSatellites(List<Satellite> satellites) {
        this.satellites = new ArrayList<>();
        this.parcialRequests = new ConcurrentHashMap<>();

        for(Satellite s : satellites) {
            this.satellites.add(s);
        }
    }

    public static SatellitesManager getInstance() {
        if (instance == null) {
            instance = new SatellitesManager();

        }
        return instance;
    }

    public List<Satellite> getSatellites(){
        return this.satellites;
    }

    public Satellite getSatelliteByName(String satelliteName){

        for (Satellite satellite : this.satellites) {

            if (satellite.getName().equalsIgnoreCase(satelliteName)) {
                return satellite;
            }
        }

        return null;

    }


    private Position getLocation(List<Distance> distances)
            throws InsuffientDistancesArgumentException {

        if(distances.size() < 3) {
            throw new InsuffientDistancesArgumentException("se requieren al menos la distancia de los 3 satelites");
        }

        return TrilaterationHelper.calcular(distances);
    }

    public TopSecretResponse calcPosition(TopSecretRequest request)
            throws InsuffientDistancesArgumentException, SattelliteNotFoundException, MessageArgumentNullException {
        TopSecretResponse res = new TopSecretResponse();

        List<Distance> dis = new ArrayList<>();
        List<String[]> messages = new ArrayList<>();
        Satellite s;
        Distance distance;

        for (TopSecretSatelliteRequest sr : request.getSatellites()) {
            s = this.getSatelliteByName(sr.getName().trim());
            if( s == null )
                throw new SattelliteNotFoundException(String.format("El satelite %s no se encuentra disponible su ubicacion", sr.getName()));
            distance = new Distance(s.getName(), s.getLocation(), sr.getDistance());
            dis.add(distance);

            messages.add(sr.getMessage());

        }

        res.setPosition( this.getLocation( dis ) );
        res.setMessage( MessageBuilder.getMessage( messages.toArray(new String[messages.size()][0]) ) );

        return res;
    }

    public void addOneSatelliteDistance(String satelliteName, TopSecretRequest request) throws MissingSatelliteException {
        if(satelliteName == null || satelliteName.trim().length() == 0) {
            throw new MissingSatelliteException("");
        }

        Satellite satellite = this.getSatelliteByName(satelliteName.trim());

            this.parcialRequests.put(satelliteName, request.getSatellites().get(0));

    }

     public TopSecretResponse getLocationFromPartialRequest() throws InsuffientDistancesArgumentException, MessageArgumentNullException {
         if(this.parcialRequests.size() < 3) {
            throw new InsuffientDistancesArgumentException("Se requieren al menos la distancia hacia 3 satelites");
         }

         List<Distance> dis = new ArrayList<>();
         List<String[]> messages = new ArrayList<>();
         TopSecretSatelliteRequest tsr;
         Satellite satellite;
         Distance distance;

         for (String satelliteName : this.parcialRequests.keySet()) {
             tsr = this.parcialRequests.get(satelliteName);
             satellite = this.getSatelliteByName(satelliteName.trim());
             distance = new Distance(tsr.getName(), satellite.getLocation(), tsr.getDistance());
             dis.add(distance);

             messages.add(tsr.getMessage());

         }

         TopSecretResponse res = new TopSecretResponse();

         res.setPosition( this.getLocation( dis ) );
         res.setMessage( MessageBuilder.getMessage( messages.toArray(new String[messages.size()][0]) ) );

         this.parcialRequests.clear();

         return res;
     }
}
