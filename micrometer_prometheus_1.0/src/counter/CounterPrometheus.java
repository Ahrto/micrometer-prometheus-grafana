package counter;

import java.time.Duration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;


public class CounterPrometheus {
	//	Para implementarlo y utilizar las funciones es necesario crear una instancia de PrometheusMeterRegistry (con la configuración por defecto) en el lugar donde queramos usarlas (Main en este caso):
	private PrometheusMeterRegistry miPrometheus = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
	private Timer timer;


	public CounterPrometheus() {
		super();
		miPrometheus = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
		timer = Timer.builder("timer.counter").register(miPrometheus);

	}
	
	

	public PrometheusMeterRegistry getPrometheusMeterRegistry() {
		return miPrometheus;
	}



	public void setPrometheusMeterRegistry(PrometheusMeterRegistry prometheusMeterRegistry) {
		this.miPrometheus = prometheusMeterRegistry;
	}



	//	Funcion para crear counters con un nombre pasado por parámetro
	public Counter createCounter(PrometheusMeterRegistry prometheusMeterRegistry, String counterName) {
		return prometheusMeterRegistry.counter(counterName);
	}

//		Función para crear counters predefinidos
	public void createCounter() {	 
		miPrometheus.counter("def.counter.true");
		miPrometheus.counter("def.counter.false");	
	}


	public void incrementTimer(Duration duration) {
		timer.record(duration);
	}
	
	public Timer getTimer() {
		return timer;
	}


	//	Funcion para incrementar counter en 1
	public void incrementCounter(Counter counter) {
		counter.increment();
	}

	//	Funcion para incrementar counter en n
	public void incrementCounter(Counter counter, int increment) {
		counter.increment(increment);
	}

	//	Funcion que recoge por parametro un booleano y actua en consecuencia
	public void isTrue (boolean b, Counter counterTrue, Counter counterFalse ) {
		if (b) {
			incrementCounter(counterTrue);
		}else {
			incrementCounter(counterFalse);
		}
	}

}
