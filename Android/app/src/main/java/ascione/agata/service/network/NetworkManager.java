package ascione.agata.service.network;

import android.graphics.Bitmap;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import ascione.agata.BuildConfig;
import ascione.agata.service.network.request.BackEndAttributeRequest;
import ascione.agata.service.network.request.BugRequest;
import ascione.agata.service.network.request.CategoryRequest;
import ascione.agata.service.network.request.CreaFrontEndRequest;
import ascione.agata.service.network.request.CreaProgettoRequest;
import ascione.agata.service.network.request.EndpointRequest;
import ascione.agata.service.network.request.FrontEndAttributeRequest;
import ascione.agata.service.network.request.IdRequest;
import ascione.agata.service.network.request.LoginRequest;
import ascione.agata.service.network.request.RegistrationRequest;
import ascione.agata.service.network.request.UpdateStatusRequest;
import ascione.agata.service.network.response.BackEndAttributeResponse;
import ascione.agata.service.network.response.BackEndCreateAttributeResponse;
import ascione.agata.service.network.response.BackEndResponse;
import ascione.agata.service.network.response.BugsCategoryResponse;
import ascione.agata.service.network.response.BugsRecordResponse;
import ascione.agata.service.network.response.BugsResponse;
import ascione.agata.service.network.response.DesignImageResponse;
import ascione.agata.service.network.response.DesignResponse;
import ascione.agata.service.network.response.EndpointRecordResponse;
import ascione.agata.service.network.response.EndpointResponse;
import ascione.agata.service.network.response.ErrorResponse;
import ascione.agata.service.network.response.FrontEndAttributeResponse;
import ascione.agata.service.network.response.FrontEndCreateResponse;
import ascione.agata.service.network.response.FrontEndResponse;
import ascione.agata.service.network.response.ProgettiRecordResponse;
import ascione.agata.service.network.response.ProgettiResponse;
import ascione.agata.service.network.response.Response;
import ascione.agata.service.network.response.UserListResponse;
import ascione.agata.service.network.response.UserResponse;
import ascione.agata.service.singleton.UserSingleton;

public class NetworkManager {

    private static final String BASE_URL = "https://therichesthumanintheworld.com/api/";
    // Timeout chiamate REST
    public static final int TIMEOUT_IN_SECONDS = 60;

    public static void login(LoginRequest userLogin, final CallbackResponseRestService<UserResponse> callbackResponseRestService){


        String url = BASE_URL + "user/login.php";
        Gson gson = new Gson();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(gson.toJson(userLogin, LoginRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post(url)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        UserResponse loginResponse = gson.fromJson(response.toString(), UserResponse.class);
                        Log.d("RISPOSTA",loginResponse + "");
                        callbackResponseRestService.onSuccess(loginResponse);
                    }

                    @Override
                    public void onError(ANError anError) {
                        callbackResponseRestService.onError(anError.getErrorCode(),"");
                        anError.printStackTrace();
                    }
                });
    }

    public static void register(RegistrationRequest userLogin, final CallbackResponseRestService<Response> callbackResponseRestService){


        String url = BASE_URL + "user/registration.php";
        Gson gson = new Gson();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(gson.toJson(userLogin, RegistrationRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post(url)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Response loginResponse = gson.fromJson(response.toString(), Response.class);
                        Log.d("RISPOSTA",loginResponse + "");
                        callbackResponseRestService.onSuccess(loginResponse);
                    }

                    @Override
                    public void onError(ANError anError) {
                        callbackResponseRestService.onError(anError.getErrorCode(),"");
                        anError.printStackTrace();
                    }
                });
    }

    public static void listProgetti(String idUser, final CallbackResponseRestService<ProgettiResponse> callbackResponseRestService) {
        String url = BASE_URL + "project/list_user.php" + "?id_user=" + idUser;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        AndroidNetworking.get(url)
                .addHeaders("Authentication-Token",token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        ProgettiResponse risposta = gson.fromJson(response.toString(), ProgettiResponse.class);
                        Log.d("RISPOSTA",risposta + "");
                        callbackResponseRestService.onSuccess(risposta);
                    }

                    @Override
                    public void onError(ANError anError) {
                        callbackResponseRestService.onError(anError.getErrorCode(),"");
                        anError.printStackTrace();
                    }
                });
    }

    public static void listBugs(String idProject, final CallbackResponseRestService<BugsResponse> callbackResponseRestService) {
        String url = BASE_URL + "bugs/list.php" + "?id_project=" + idProject;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        AndroidNetworking.get(url)
                .addHeaders("Authentication-Token",token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        BugsResponse risposta = gson.fromJson(response.toString(), BugsResponse.class);
                        Log.d("RISPOSTA",risposta + "");
                        callbackResponseRestService.onSuccess(risposta);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse risposta = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),risposta.getError());
                        anError.printStackTrace();
                    }
                });
    }


    public static void listDesign(String idProject, final CallbackResponseRestService<DesignResponse> callbackResponseRestService) {
        String url = BASE_URL + "design/list.php" + "?id_project=" + idProject;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        AndroidNetworking.get(url)
                .addHeaders("Authentication-Token",token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        DesignResponse risposta = gson.fromJson(response.toString(), DesignResponse.class);
                        Log.d("RISPOSTA",risposta + "");
                        callbackResponseRestService.onSuccess(risposta);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse risposta = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),risposta.getError());
                        anError.printStackTrace();
                    }
                });
    }

    public static void listFrontEnd(String idProject, final CallbackResponseRestService<FrontEndResponse> callbackResponseRestService) {
        String url = BASE_URL + "front-end/list.php" + "?id_project=" + idProject;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        AndroidNetworking.get(url)
                .addHeaders("Authentication-Token",token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        FrontEndResponse risposta = gson.fromJson(response.toString(), FrontEndResponse.class);
                        Log.d("RISPOSTA",risposta + "");
                        callbackResponseRestService.onSuccess(risposta);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse risposta = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),risposta.getError());
                        anError.printStackTrace();
                    }
                });
    }

    public static void listEndpoint(String idProject, final CallbackResponseRestService<EndpointResponse> callbackResponseRestService) {
        String url = BASE_URL + "back-end/list-endpoint.php" + "?id_project=" + idProject;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        AndroidNetworking.get(url)
                .addHeaders("Authentication-Token",token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        EndpointResponse risposta = gson.fromJson(response.toString(), EndpointResponse.class);
                        Log.d("RISPOSTA",risposta + "");
                        callbackResponseRestService.onSuccess(risposta);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse risposta = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),risposta.getError());
                        anError.printStackTrace();
                    }
                });
    }


    public static void listBackEndAPI(String idProject, final CallbackResponseRestService<BackEndResponse> callbackResponseRestService) {
        String url = BASE_URL + "back-end/list.php" + "?id_project=" + idProject;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        AndroidNetworking.get(url)
                .addHeaders("Authentication-Token",token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        BackEndResponse risposta = gson.fromJson(response.toString(), BackEndResponse.class);
                        Log.d("RISPOSTA",risposta + "");
                        callbackResponseRestService.onSuccess(risposta);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse risposta = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),risposta.getError());
                        anError.printStackTrace();
                    }
                });
    }



    public static void deleteFrontEnd(String id, final CallbackResponseRestService<Boolean> callbackResponseRestService) {
        String url = BASE_URL + "front-end/delete.php" ;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        Gson gson = new Gson();
        JSONObject requestBody = null;
        IdRequest idRequest = new IdRequest(id);
        try {
            requestBody = new JSONObject(gson.toJson(idRequest, IdRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callbackResponseRestService.onSuccess(true);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse risposta = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),risposta.getError());
                        anError.printStackTrace();
                    }
                });
    }

    public static void updateStatusFrontEnd(String id, String status, final CallbackResponseRestService<Boolean> callbackResponseRestService) {
        String url = BASE_URL + "front-end/update_status.php" ;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        Gson gson = new Gson();
        JSONObject requestBody = null;
        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest(id,status);
        try {
            requestBody = new JSONObject(gson.toJson(updateStatusRequest, UpdateStatusRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callbackResponseRestService.onSuccess(true);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse risposta = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),risposta.getError());
                        anError.printStackTrace();
                    }
                });
    }

    public static void updateStatusBackEnd(String id, String status, final CallbackResponseRestService<Boolean> callbackResponseRestService) {
        String url = BASE_URL + "back-end/update_status.php" ;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        Gson gson = new Gson();
        JSONObject requestBody = null;
        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest(id,status);
        try {
            requestBody = new JSONObject(gson.toJson(updateStatusRequest, UpdateStatusRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callbackResponseRestService.onSuccess(true);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse risposta = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),risposta.getError());
                        anError.printStackTrace();
                    }
                });
    }


    public static void addFrontEnd(CreaFrontEndRequest creaFrontEndRequest, final CallbackResponseRestService<FrontEndCreateResponse> callbackResponseRestService) {
        String url = BASE_URL + "front-end/insert.php" ;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        Gson gson = new Gson();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(gson.toJson(creaFrontEndRequest, CreaFrontEndRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        FrontEndCreateResponse risposta = gson.fromJson(response.toString(), FrontEndCreateResponse.class);
                        Log.d("RISPOSTA",risposta + "");

                        callbackResponseRestService.onSuccess(risposta);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse risposta = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),risposta.getError());
                        anError.printStackTrace();
                    }
                });
    }

    public static void addBackEnd(CreaFrontEndRequest creaFrontEndRequest, final CallbackResponseRestService<FrontEndCreateResponse> callbackResponseRestService) {
        String url = BASE_URL + "back-end/insert.php" ;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        Gson gson = new Gson();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(gson.toJson(creaFrontEndRequest, CreaFrontEndRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        FrontEndCreateResponse risposta = gson.fromJson(response.toString(), FrontEndCreateResponse.class);
                        Log.d("RISPOSTA",risposta + "");
                        callbackResponseRestService.onSuccess(risposta);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse risposta = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),risposta.getError());
                        anError.printStackTrace();
                    }
                });
    }

    public static void addAttBackEnd(BackEndAttributeRequest creaFrontEndRequest, final CallbackResponseRestService<BackEndCreateAttributeResponse> callbackResponseRestService) {
        String url = BASE_URL + "back-end/insert_attributes.php" ;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        Gson gson = new Gson();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(gson.toJson(creaFrontEndRequest, BackEndAttributeRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        BackEndCreateAttributeResponse risposta = gson.fromJson(response.toString(), BackEndCreateAttributeResponse.class);
                        Log.d("RISPOSTA",risposta + "");
                        callbackResponseRestService.onSuccess(risposta);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse risposta = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),risposta.getError());
                        anError.printStackTrace();
                    }
                });
    }


    public static void addProgetto(CreaProgettoRequest creaFrontEndRequest, final CallbackResponseRestService<ProgettiRecordResponse> callbackResponseRestService) {
        String url = BASE_URL + "project/create.php" ;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        Gson gson = new Gson();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(gson.toJson(creaFrontEndRequest, CreaProgettoRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        ProgettiRecordResponse risposta = gson.fromJson(response.toString(), ProgettiRecordResponse.class);
                        Log.d("RISPOSTA",risposta + "");
                        callbackResponseRestService.onSuccess(risposta);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse risposta = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),risposta.getError());
                        anError.printStackTrace();
                    }
                });
    }


    public static void addAttributesFrontEnd(FrontEndAttributeRequest creaFrontEndRequest, final CallbackResponseRestService<Boolean> callbackResponseRestService) {
        String url = BASE_URL + "front-end/insert_attribute.php" ;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        Gson gson = new Gson();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(gson.toJson(creaFrontEndRequest, FrontEndAttributeRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addStringBody(requestBody.toString())
               .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callbackResponseRestService.onSuccess(true);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse risposta = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),risposta.getError());
                        anError.printStackTrace();
                    }
                });
    }


    public static void listUsers(final CallbackResponseRestService<UserListResponse> callbackResponseRestService) {
        String url = BASE_URL + "user/list.php" ;
        String token = UserSingleton.getInstance().getUser().getUserToken();
        AndroidNetworking.get(url)
                .addHeaders("Authentication-Token",token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        UserListResponse risposta = gson.fromJson(response.toString(), UserListResponse.class);
                        Log.d("RISPOSTA",risposta + "");
                        callbackResponseRestService.onSuccess(risposta);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse errorResponse = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),errorResponse.getError());
                        anError.printStackTrace();
                    }
                });
    }

    // Chiamata per scaricare l'immagine come Bitmap da un URL
    public static void downloadImage(String nome, final CallbackResponseRestService<Bitmap> callbackResponseRestService) {
        String url = BASE_URL + "profile/uploads/" + nome;

        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsBitmap(new BitmapRequestListener() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Log.i("INFO", "DOWNLOAD IMAGE FROM URL DONE WITH SUCCESS");
                        callbackResponseRestService.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.i("INFO", "DOWNLOAD IMAGE FROM URL WITH ERROR" + anError);
                        callbackResponseRestService.onError(anError.getErrorCode(),"");
                    }
                });
    }


    public static void addBugInCategory(BugRequest bugRequest, final CallbackResponseRestService<BugsRecordResponse> callbackResponseRestService){


        String url = BASE_URL + "bugs/insert.php";
        Gson gson = new Gson();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(gson.toJson(bugRequest, BugRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String token = UserSingleton.getInstance().getUser().getUserToken();



        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        BugsRecordResponse response1 = gson.fromJson(response.toString(), BugsRecordResponse.class);
                        Log.d("RISPOSTA",response1 + "");
                        callbackResponseRestService.onSuccess(response1);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse response1 = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),response1.getError());
                        anError.printStackTrace();
                    }
                });
    }

    public static void addBugCategory(CategoryRequest categoryRequest, final CallbackResponseRestService<BugsCategoryResponse> callbackResponseRestService){


        String url = BASE_URL + "bugs/insert_category.php";
        Gson gson = new Gson();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(gson.toJson(categoryRequest, CategoryRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String token = UserSingleton.getInstance().getUser().getUserToken();



        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        BugsCategoryResponse response1 = gson.fromJson(response.toString(), BugsCategoryResponse.class);
                        Log.d("RISPOSTA",response1 + "");
                        callbackResponseRestService.onSuccess(response1);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse response1 = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),response1.getError());
                        anError.printStackTrace();
                    }
                });
    }

    public static void addDesignCategory(CategoryRequest categoryRequest, final CallbackResponseRestService<BugsCategoryResponse> callbackResponseRestService){


        String url = BASE_URL + "design/insert_category.php";
        Gson gson = new Gson();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(gson.toJson(categoryRequest, CategoryRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String token = UserSingleton.getInstance().getUser().getUserToken();



        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        BugsCategoryResponse response1 = gson.fromJson(response.toString(), BugsCategoryResponse.class);
                        Log.d("RISPOSTA",response1 + "");
                        callbackResponseRestService.onSuccess(response1);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse response1 = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),response1.getError());
                        anError.printStackTrace();
                    }
                });
    }

    public static void addEndpoint(EndpointRequest categoryRequest, final CallbackResponseRestService<EndpointRecordResponse> callbackResponseRestService){


        String url = BASE_URL + "back-end/insert-endpoint.php";
        Gson gson = new Gson();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(gson.toJson(categoryRequest, EndpointRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String token = UserSingleton.getInstance().getUser().getUserToken();



        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        EndpointRecordResponse response1 = gson.fromJson(response.toString(), EndpointRecordResponse.class);
                        Log.d("RISPOSTA",response1 + "");
                        callbackResponseRestService.onSuccess(response1);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse response1 = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),response1.getError());
                        anError.printStackTrace();
                    }
                });
    }


    public static void deleteBug(IdRequest idRequest, final CallbackResponseRestService<Boolean> callbackResponseRestService){


        String url = BASE_URL + "bugs/delete.php";
        Gson gson = new Gson();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(gson.toJson(idRequest, IdRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String token = UserSingleton.getInstance().getUser().getUserToken();



        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callbackResponseRestService.onSuccess(true);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse response1 = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),response1.getError());
                        anError.printStackTrace();
                    }
                });
    }

    public static void deleteEndpoint(IdRequest idRequest, final CallbackResponseRestService<Boolean> callbackResponseRestService){


        String url = BASE_URL + "back-end/delete-endpoint.php";
        Gson gson = new Gson();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(gson.toJson(idRequest, IdRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String token = UserSingleton.getInstance().getUser().getUserToken();



        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callbackResponseRestService.onSuccess(true);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse response1 = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),response1.getError());
                        anError.printStackTrace();
                    }
                });
    }

    public static void deleteBackEnd(IdRequest idRequest, final CallbackResponseRestService<Boolean> callbackResponseRestService){


        String url = BASE_URL + "back-end/delete-backend.php";
        Gson gson = new Gson();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(gson.toJson(idRequest, IdRequest.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String token = UserSingleton.getInstance().getUser().getUserToken();



        AndroidNetworking.post(url)
                .addHeaders("Authentication-Token",token)
                .addJSONObjectBody(requestBody)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callbackResponseRestService.onSuccess(true);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse response1 = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode(),response1.getError());
                        anError.printStackTrace();
                    }
                });
    }


    public static void uploadImageDesign(String id_project, String id_category, File image, final CallbackResponseRestService<DesignImageResponse> callbackResponseRestService) {

        String url = BASE_URL + "design/upload.php" + "?id_project=" + id_project + "&id_category=" + id_category;
        String token = UserSingleton.getInstance().getUser().getUserToken();

        AndroidNetworking.upload(url)
                .addHeaders("Authentication-Token",token)
                .addMultipartFile("image_name",image)
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        DesignImageResponse response1 = gson.fromJson(response.toString(), DesignImageResponse.class);
                        Log.d("RISPOSTA",response1 + "");
                        callbackResponseRestService.onSuccess(response1);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson gson = new Gson();
                        ErrorResponse response1 = gson.fromJson(anError.getErrorBody(), ErrorResponse.class);
                        callbackResponseRestService.onError(anError.getErrorCode()," ");
                        anError.printStackTrace();
                    }
                });



    }


}
