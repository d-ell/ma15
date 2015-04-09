package ma15.brickcollector.connection;

public interface Callback {

	public void handleResponse(String requestMethod, String xml);
}
