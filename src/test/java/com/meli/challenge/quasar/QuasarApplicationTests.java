package com.meli.challenge.quasar;

import com.meli.challenge.quasar.controllers.QuasarController;
import com.meli.challenge.quasar.entities.*;
import com.meli.challenge.quasar.excepciones.InsuffientDistancesArgumentException;
import com.meli.challenge.quasar.excepciones.MessageArgumentNullException;
import com.meli.challenge.quasar.excepciones.MissingSatelliteException;
import com.meli.challenge.quasar.excepciones.SattelliteNotFoundException;
import com.meli.challenge.quasar.helpers.MessageBuilder;
import com.meli.challenge.quasar.helpers.SatellitesManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuasarApplicationTests {

	@Autowired
	private  QuasarController quasarController;
	@Test
	void contextLoads() {
		//TopSecretSatelliteRequest uno = new TopSecretSatelliteRequest("kenobi", 100, new String[]{"este", "", "", "mensaje", ""});
		//TopSecretSatelliteRequest dos = new TopSecretSatelliteRequest("skywalker", 115.5, new String[]{"", "es", "", "", "secreto"});
		//TopSecretSatelliteRequest tres = new TopSecretSatelliteRequest("sato", 142.7, new String[]{"este", "", "un", "", ""});

	}

	/*void getInformation_messageNotDeterminated() {

		// kenobi
		TopSecretSplitRequest request =
				TopSecretSplitRequest.builder().distance(DISTANCE_TO_KENOBI).message(messageS1).build();
		challengeService.saveInformation(KENOBI, request, IP_ADDRESS);

		// skywalker
		request = TopSecretSplitRequest.builder().distance(DISTANCE_TO_SKYWALKER).message(messageS2).build();
		challengeService.saveInformation(SKYWALKER, request, IP_ADDRESS);

		// sato
		messageS3 = new String[]{"este", "", "mensaje", ""};
		request = TopSecretSplitRequest.builder().distance(DISTANCE_TO_SATO).message(messageS3).build();
		challengeService.saveInformation(SATO, request, IP_ADDRESS);

		Exception exception = assertThrows(MessageNotDeterminedException.class, () -> {
			challengeService.getInformation(IP_ADDRESS);
		});
		String expectedMessage = ConsUtil.MESSAGE_NOT_DETERMINED;
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}*/

	/* getMessage */

	@Test
	void getMessageTest_messageOk() throws MessageArgumentNullException {
		String[] msg1 = new String[]{"este", "es", "un", "mensaje"};
		String[] msg2 = new String[]{"", "", "un", "mensaje"};
		String[] msg3 = new String[]{"", "es", "", ""};
		String res = MessageBuilder.getMessage(msg1, msg2, msg3);
		assertEquals("este es un mensaje", res);
	}

	@Test
	void getMessageTest_ArraySizesAreNotEqual() {
		String[] msg1 = new String[]{"este", "", "un", "mensaje"};
		String[] msg2 = new String[]{"este", "", "un" };
		String[] msg3 = new String[]{"", "es", "", "mensaje", ""};
		Exception exception = assertThrows(MessageArgumentNullException.class, () -> MessageBuilder.getMessage(msg1, msg2, msg3));
		String expectedMessage = "Inconsistencia en la cantidad de elementos";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void getMessageTest_NoElementsOnFirstArray() {
		String[] msg1 = new String[]{};
		String[] msg2 = new String[]{"este", "", "un" };
		String[] msg3 = new String[]{"", "es", "", "mensaje", ""};
		Exception exception = assertThrows(MessageArgumentNullException.class, () -> {
			MessageBuilder.getMessage(msg1, msg2, msg3);
		});
		String expectedMessage = "No se encontraron mensajes";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void topSecret_caculatePositionCaseEnunciado () throws InsuffientDistancesArgumentException, MessageArgumentNullException {

		SatellitesManager.getInstance().setDefaultSatellites();
		List<TopSecretSatelliteRequest> satellites = new ArrayList<>();
		satellites.add(new TopSecretSatelliteRequest("kenobi", 100, new String[]{"este", "", "", "mensaje", ""}));
		satellites.add(new TopSecretSatelliteRequest("skywalker", 115.5f, new String[]{"", "es", "", "", "secreto"}));
		satellites.add(new TopSecretSatelliteRequest("sato", 142.7f, new String[]{"este", "", "un", "", ""}));

		TopSecretRequest request = new TopSecretRequest();
		request.setSatellites( satellites );

		String expectedMessage = "este es un mensaje secreto";
		Position expectedPosition = new Position(-487.29, 1557.01);


		ResponseEntity<TopSecretResponse> response = quasarController.topSecret(request);
		assertEquals( expectedMessage, response.getBody().getMessage() );
		assertEquals( expectedPosition.getX(), response.getBody().getPosition().getX() );
		assertEquals( expectedPosition.getY(), response.getBody().getPosition().getY() );

	}

	@Test
	void topSecret_caculatePositionCaseEnunciadoMissingSatellite () throws InsuffientDistancesArgumentException, MessageArgumentNullException, SattelliteNotFoundException {

		SatellitesManager.getInstance().setDefaultSatellites();
		List<TopSecretSatelliteRequest> satellites = new ArrayList<>();
		satellites.add(new TopSecretSatelliteRequest("satiano", 100, new String[]{"este", "", "", "mensaje", ""}));
		satellites.add(new TopSecretSatelliteRequest("skywalker", 115.5f, new String[]{"", "es", "", "", "secreto"}));
		satellites.add(new TopSecretSatelliteRequest("sato", 142.7f, new String[]{"este", "", "un", "", ""}));

		TopSecretRequest request = new TopSecretRequest();
		request.setSatellites( satellites );

		String expectedMessage = "El satelite satiano no se encuentra disponible su ubicacion";
		Position expectedPosition = new Position(-487.29, 1557.01);


		Exception exception = assertThrows(SattelliteNotFoundException.class, () ->   SatellitesManager.getInstance().calcPosition(request));
		assertEquals( expectedMessage, exception.getMessage() );

	}

	@Test
	void topSecret_caculatePositionCase1 () throws InsuffientDistancesArgumentException, MessageArgumentNullException {

		/*
		double[] pA = { 832.16, 5148 };
        double[] pB = { 741.26, 5242.3 };
        double[] pC = { 863.76, 5245.1 };

        double[] a = trilaterate2DLinear(pA, pB, pC, 86.814, 69.409, 55.448);

		//resultado: 809,8941740502759, 5231,915345479004
		 */

		List<Satellite>  satellites = new ArrayList<>();
		satellites.add(new Satellite("kenobi", new Position(832.16, 5148)));
		satellites.add(new Satellite("Skywalker", new Position(741.26, 5242.3)));
		satellites.add(new Satellite("Sato", new Position(863.76, 5245.1 )));
		SatellitesManager.getInstance().setSatellites(satellites);


		List<TopSecretSatelliteRequest> request = new ArrayList<>();
		request.add(new TopSecretSatelliteRequest("kenobi", 86.814f, new String[]{"este", "", "", "mensaje", ""}));
		request.add(new TopSecretSatelliteRequest("skywalker", 69.409f, new String[]{"", "es", "", "", "secreto"}));
		request.add(new TopSecretSatelliteRequest("sato", 55.448f, new String[]{"este", "", "un", "", ""}));

		TopSecretRequest req = new TopSecretRequest();
		req.setSatellites( request );

		String expectedMessage = "este es un mensaje secreto";
		Position expectedPosition = new Position(809.89,5231.92);


		ResponseEntity<TopSecretResponse> response = quasarController.topSecret(req);
		assertEquals( expectedMessage, response.getBody().getMessage() );
		assertEquals( expectedPosition.getX(), response.getBody().getPosition().getX() );
		assertEquals( expectedPosition.getY(), response.getBody().getPosition().getY() );

	}

	@Test
	void topSecret_caculatePositionCase2 () throws InsuffientDistancesArgumentException, MessageArgumentNullException {

		/*
        double[] ejeA = { 14, 45 };
        double[] ejeB = { 80, 70 };
        double[] ejeC = { 71, 50 };

		double[] b = trilaterate2DLinear(ejeA, ejeB, ejeC, 39, 50, 29);
		////resultado: 50.000000000000014, 29.999999999999943
		 */

		List<Satellite>  satellites = new ArrayList<>();
		satellites.add(new Satellite("kenobi", new Position(14, 45)));
		satellites.add(new Satellite("Skywalker", new Position(80, 70)));
		satellites.add(new Satellite("Sato", new Position(71, 50 )));
		SatellitesManager.getInstance().setSatellites(satellites);


		List<TopSecretSatelliteRequest> request = new ArrayList<>();
		request.add(new TopSecretSatelliteRequest("kenobi", 39f, new String[]{"este", "", "", "mensaje", ""}));
		request.add(new TopSecretSatelliteRequest("skywalker", 50f, new String[]{"", "es", "", "", "secreto"}));
		request.add(new TopSecretSatelliteRequest("sato", 29f, new String[]{"este", "", "un", "", ""}));

		TopSecretRequest req = new TopSecretRequest();
		req.setSatellites( request );

		String expectedMessage = "este es un mensaje secreto";
		Position expectedPosition = new Position(50,30);


		ResponseEntity<TopSecretResponse> response = quasarController.topSecret(req);
		assertEquals( expectedMessage, response.getBody().getMessage() );
		assertEquals( expectedPosition.getX(), response.getBody().getPosition().getX() );
		assertEquals( expectedPosition.getY(), response.getBody().getPosition().getY() );

	}

	@Test
	void topSecret_caculatePositionInsuficientDistancesException () throws InsuffientDistancesArgumentException, MessageArgumentNullException {

		SatellitesManager.getInstance().setDefaultSatellites();
		List<TopSecretSatelliteRequest> distances = new ArrayList<>();
		distances.add(new TopSecretSatelliteRequest("kenobi", 100, new String[]{"este", "", "", "mensaje", ""}));
		distances.add(new TopSecretSatelliteRequest("skywalker", 115.5f, new String[]{"", "es", "", "", "secreto"}));
		//satellites.add(new TopSecretSatelliteRequest("sato", 142.7f, new String[]{"este", "", "un", "", ""}));

		TopSecretRequest req = new TopSecretRequest();
		req.setSatellites( distances );

		String expectedMessage = "se requieren al menos la distancia de los 3 satelites";
		Position expectedPosition = new Position(50,30);


		Exception exception = assertThrows(InsuffientDistancesArgumentException.class, () ->  SatellitesManager.getInstance().calcPosition(req));
		assertEquals( expectedMessage, exception.getMessage() );


	}

	@Test
	void topSecret_caculatePositionMessagesException () throws InsuffientDistancesArgumentException, MessageArgumentNullException {

		SatellitesManager.getInstance().setDefaultSatellites();
		List<TopSecretSatelliteRequest> distances = new ArrayList<>();
		distances.add(new TopSecretSatelliteRequest("kenobi", 100, new String[]{"este", "", "", "mensaje", ""}));
		distances.add(new TopSecretSatelliteRequest("skywalker", 115.5f, new String[]{"", "es", "", "", "secreto"}));
		distances.add(new TopSecretSatelliteRequest("sato", 142.7f, new String[]{"este", "", "un", ""}));

		TopSecretRequest req = new TopSecretRequest();
		req.setSatellites( distances );

		String expectedMessage = "Inconsistencia en la cantidad de elementos";
		Position expectedPosition = new Position(50,30);


		Exception exception = assertThrows(MessageArgumentNullException.class, () ->  SatellitesManager.getInstance().calcPosition(req));
		assertEquals( expectedMessage, exception.getMessage() );

	}

	@Test
	void topSecret_caculatePositionMessagesFirstArrayMissingException () throws InsuffientDistancesArgumentException, MessageArgumentNullException {

		SatellitesManager.getInstance().setDefaultSatellites();
		List<TopSecretSatelliteRequest> distances = new ArrayList<>();
		distances.add(new TopSecretSatelliteRequest("kenobi", 100, null));
		distances.add(new TopSecretSatelliteRequest("skywalker", 115.5f, new String[]{"", "es", "", "", "secreto"}));
		distances.add(new TopSecretSatelliteRequest("sato", 142.7f, new String[]{"este", "", "un", ""}));

		TopSecretRequest req = new TopSecretRequest();
		req.setSatellites( distances );

		String expectedMessage = "No se encontraron mensajes";
		Position expectedPosition = new Position(50,30);


		Exception exception = assertThrows(MessageArgumentNullException.class, () ->  SatellitesManager.getInstance().calcPosition(req));
		assertEquals( expectedMessage, exception.getMessage() );

	}

	@Test
	void getLocation_TopSecreSplit() throws MissingSatelliteException, InsuffientDistancesArgumentException, MessageArgumentNullException {

		// kenobi
		SatellitesManager.getInstance().setDefaultSatellites();
		List<TopSecretSatelliteRequest> distances = new ArrayList<>();
		TopSecretRequest req = new TopSecretRequest();

		distances.add(new TopSecretSatelliteRequest("kenobi", 100, new String[]{"este", "", "", "mensaje", ""}));
		req.setSatellites( distances );
		SatellitesManager.getInstance().addOneSatelliteDistance("kenobi", req);

		// skywalker
		distances.clear();
		distances.add(new TopSecretSatelliteRequest("skywalker", 115.5f, new String[]{"", "es", "", "", "secreto"}));
		req.setSatellites( distances );
		SatellitesManager.getInstance().addOneSatelliteDistance("skywalker", req);

		// sato
		distances.clear();
		distances.add(new TopSecretSatelliteRequest("sato", 142.7f, new String[]{"este", "", "un", "", ""}));
		SatellitesManager.getInstance().addOneSatelliteDistance("sato", req);

		TopSecretResponse response = SatellitesManager.getInstance().getLocationFromPartialRequest();
		Position expectedPosition = new Position(-487.29, 1557.01);
		assertEquals( expectedPosition.getX(), response.getPosition().getX() );
		assertEquals( expectedPosition.getY(), response.getPosition().getY() );
		assertEquals("este es un mensaje secreto", response.getMessage());
	}

	@Test
	void getLocation_TopSecreSplit_InsuficientDistances() throws MissingSatelliteException, InsuffientDistancesArgumentException, MessageArgumentNullException {

		SatellitesManager.getInstance().setDefaultSatellites();
		// kenobi
		List<TopSecretSatelliteRequest> distances = new ArrayList<>();
		TopSecretRequest req = new TopSecretRequest();

		distances.add(new TopSecretSatelliteRequest("kenobi", 100, new String[]{"este", "", "", "mensaje", ""}));
		req.setSatellites( distances );
		SatellitesManager.getInstance().addOneSatelliteDistance("kenobi", req);

		// skywalker
		distances.clear();
		distances.add(new TopSecretSatelliteRequest("skywalker", 115.5f, new String[]{"", "es", "", "", "secreto"}));
		req.setSatellites( distances );
		SatellitesManager.getInstance().addOneSatelliteDistance("skywalker", req);

		// sato
		/*distances.clear();
		distances.add(new TopSecretSatelliteRequest("sato", 142.7f, new String[]{"este", "", "un", "", ""}));
		SatellitesManager.getInstance().addOneSatelliteDistance("sato", req);*/


		Exception exception = assertThrows(InsuffientDistancesArgumentException.class, () ->  SatellitesManager.getInstance().getLocationFromPartialRequest());


		assertEquals("Se requieren al menos la distancia hacia 3 satelites", exception.getMessage());
	}

}
