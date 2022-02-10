package timer;

import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class TimerImpl {
	private Timer timer;
//	private PrometheusMeterRegistry prometheusMeterRegistry;

//	Solo funciona si lo pasamos por parámetro
	public Timer createTimer(PrometheusMeterRegistry prometheusMeterRegistry, String timerName) {
		timer = Timer.builder(timerName).register(prometheusMeterRegistry);
		return timer;	
	}



}
