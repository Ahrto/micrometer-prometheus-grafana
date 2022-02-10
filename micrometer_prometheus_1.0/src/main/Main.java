package main;

import java.util.Random;

import counter.CounterPrometheus;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import server.ServerHttp;
import timer.TimerImpl;

public class Main {

	public static void main(String[] args) {
		System.out.println("ok...");

		//		Es necesario crear un PrometheusMeterRegistry
//		PrometheusMeterRegistry prometheusServer = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

		CounterPrometheus counterPrometheus = new CounterPrometheus();
		
		PrometheusMeterRegistry prometheusMeterRegistry = counterPrometheus.getPrometheusMeterRegistry();
		
//		TODO dos promMeter, uno para el server y otro para los contadores
		Runnable runnable = new Runnable() {
			@Override
			public void run() {

				while (true) {

					try {

						//		Crear server
						ServerHttp serverHttp = new ServerHttp();

						//		instancia timerImpl
						TimerImpl timerImpl = new TimerImpl();

						//		Timer para controlar el tiempo que se tarda en levantar el servidor
						Timer timerServer = timerImpl.createTimer(prometheusMeterRegistry, "timer.server");

						timerServer.record(() -> {
							serverHttp.createServerHttp(prometheusMeterRegistry, "/micrometer", 8080);
						});
//						serverHttp.createServerHttp(prometheusMeterRegistry, "/micrometer", 8080);
						

//						serverHttp.createServerHttp(prometheusMeterRegistry, "/micrometer", 8080);
						//		Crear counters
						CounterPrometheus counterPrometheus = new CounterPrometheus();
						Counter counterTrue = counterPrometheus.createCounter(prometheusMeterRegistry, "counter.true");
						Counter counterFalse = counterPrometheus.createCounter(prometheusMeterRegistry, "counter.false");
						
//						ServerHttp serverHttp = new ServerHttp();
//						serverHttp.createServerHttp(prometheusServer, "/micrometer", 8080);

						//		Counters predefinidos
//						counterPrometheus.createCounter(prometheusMeterRegistry); 

						//		Crear timer
//						Timer timer = counterPrometheus.getTimer();

						Timer timerIsTrue = timerImpl.createTimer(prometheusMeterRegistry, "timer.isTrue");

						//		Establecer variable boolean
						//				boolean b = true;

						//		Valor aletario
						Random random = new Random();
						boolean b = random.nextBoolean();
						System.out.println(b);

						//								Capturar tiempo funcion isTrue
						timerIsTrue.record(() -> {
							counterPrometheus.isTrue(b, counterTrue, counterFalse);
						});
						
						
//						timer.record(duration);
						Thread.sleep(5000);

						serverHttp.closeServer();

					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

			}
		};
		// Creamos un hilo y le pasamos el runnable
		Thread hilo = new Thread(runnable);
		hilo.start();

		/*
		System.out.println("Yo imprimo en el hilo principal");
		//		Levantamos el server
		ServerHttp server = new ServerHttp();
		server.createServerHttp(prometheusMeterRegistry,"/micrometer", 8080);

		//		Clase para crear counters
		CounterImpl counterImpl = new CounterImpl();

		//		Variable Counter que almacena el counter creado
		Counter counterTrue = counterImpl.createCounter(prometheusMeterRegistry, "counter.true"); // createCounter(nombreCounter) crea un counter con el nombre deseado
		//		counterImpl.incrementCounter(counter1); //incrementCounter(nombreCounter) incrementa el counter en 1

		Counter counterFalse = counterImpl.createCounter(prometheusMeterRegistry, "counter.false");

		//		Counters predefinidos (solo se pasa por parámetro el MeterRegistry)
		counterImpl.createCounter(prometheusMeterRegistry); // Crea los counter def_counter_true y def_counter_false

		System.out.println();
		//		counterImpl.incrementCounter(counter1, 150); //a incrementCounter() se le puede pasar por parametros un counter y un valor, incrementa el counter en ese valor

		//		Se envia por parametro un booleano, si es true se incrementa un counter, si es false lo hace otro.
		boolean b = true;
		//		counterImpl.isTrue(b, counterTrue, counterFalse);

		TimerImpl timerImpl = new TimerImpl();
		Timer timer = timerImpl.createTimer(prometheusMeterRegistry, "timer.prueba");

		timer.record(() -> {
			counterImpl.isTrue(b, counterTrue, counterFalse);
		});*/



	}

}
