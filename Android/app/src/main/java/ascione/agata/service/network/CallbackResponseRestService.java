package ascione.agata.service.network;

public interface CallbackResponseRestService<T> {
    void onSuccess(T object);
    void onError(int errorCode, String error);
}