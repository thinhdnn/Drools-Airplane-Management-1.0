package pl.edu.atar.airplanemanagement;

import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.time.SessionPseudoClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.atar.airplanemanagement.events.LocationUpdateEvent;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AirplaneManagementTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirplaneManagementTest.class);

    @Test
    public void testWithSystemClock() throws InterruptedException {
        KieServices kService = KieServices.Factory.get();
        KieContainer kContainer = kService.getKieClasspathContainer();
        KieBase kBase = kContainer.getKieBase("airplane");

        KieSessionConfiguration configuration = kService.newKieSessionConfiguration();
        configuration.setOption(ClockTypeOption.PSEUDO);

        KieSession kSession = kBase.newKieSession(configuration, null);

        // Utworzenie (pseudo)zegara dla sesji
        SessionPseudoClock pclock = kSession.getSessionClock();
        pclock.advanceTime(System.currentTimeMillis(), TimeUnit.MILLISECONDS);

        // Uruchomienie wątku, który w trybie ciągłym monitoruje zdarzenia
        // oraz w przypadku, gdy przesłanki reguły są spełnione uruchamia akcje
        new Thread(kSession::fireUntilHalt).start();

        // Logowanie zebranych informacji
        kService.getLoggers().newConsoleLogger(kSession);
        kService.getLoggers().newFileLogger(kSession, "./logs/reasoning_" + pclock.getCurrentTime());

        // Symulacja generowania ciągu zdarzeń z wykorzystaniem (psuedo)zegara
        new Thread(() -> {
            try {
                long timestamp = System.currentTimeMillis();
                while (true) {

                    // WARIANT 1 - BEGIN
                    // Dodanie zdarzeń, alternatywnie zdarzenia mogą dostarczane przez odpowiedni broker np. Kafkę.
                    kSession.insert(new LocationUpdateEvent("SXS48Z", 37.8999, -108.8726, timestamp));
                    kSession.insert(new LocationUpdateEvent("UBD431", 38.1272, -107.3218, timestamp));
                    kSession.insert(new LocationUpdateEvent("WMT6TE", 39.8926, -105.1122, timestamp));
                    timestamp += 10000; // Symulacja przesunięcia czasu dla celu logów
                    //WARIANT 1 - END

                    // WARIANT 2 - BEGIN
                    //LocationLostEvent lle = new LocationLostEvent();
                    //lle.setTimestamp(Date.from(Instant.ofEpochMilli(pclock.getCurrentTime())));
                    //kSession.insert(lle);
                    // WARIANT 2 - END

                    // Przesunięcie (pseudo)zegara w przód aby symulować upływ czasu
                    pclock.advanceTime(15, TimeUnit.SECONDS);

                    // Uśpienie wątku w celu obserwowania w "slow-motion" działania silnika wnioskującego
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Dodanie możliwości poprawnego zamknięcia sesji
        Runtime.getRuntime().addShutdownHook(new Thread(kSession::halt));

    }
}