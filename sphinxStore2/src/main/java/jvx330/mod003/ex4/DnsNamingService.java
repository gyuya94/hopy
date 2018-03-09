package jvx330.mod003.ex4;

public class DnsNamingService implements NamingService {

	@Override
	public Object lookup(String name) {
		return "DNS : " + name;
	}

}
