package com.example.finalproyect;

import com.example.finalproyect.Espacio.Espacio;
import com.example.finalproyect.Espacio.EspacioResponse;
import com.example.finalproyect.login.LoginRequest;
import com.example.finalproyect.login.LoginResponse;
import com.example.finalproyect.registro.Registro;
import com.example.finalproyect.registro.RegistroResponse;
import com.example.finalproyect.registro.RegistroTarifa;
import com.example.finalproyect.rol.RolResponse;
import com.example.finalproyect.tarifa.Tarifa;
import com.example.finalproyect.tarifa.TarifaResponse;
import com.example.finalproyect.usuario.Usuario;
import com.example.finalproyect.usuario.UsuarioResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebService {

    @GET("/usuarios")
    Call<UsuarioResponse> obtenerUsuarios();

    @GET("/usuario/{id}")
    Call<String> obtenerUsuario(
            @Path("id") int id
    );

    @PUT("/usuario/actualizarEstado")
    Call<String> actualizarEstadoUsuario(@Body Usuario usuario);  // Método para actualizar el estado del usuario

    @GET("/usuario/estado/{id}")
    Call<Integer> obtenerEstadoUsuario(@Path("id") int id);



    @POST("/usuario/add")
    Call<String> agregarUsuario(
            @Body Usuario usuario
    );

    @PUT("/usuario/update/{id}")
    Call<String> actualizarUsuario(
            @Path("id") int id,
            @Body Usuario usuario
    );

    @DELETE("/usuario/delete/{id}")
    Call<String> borrarUsuario(
            @Path("id") int id
    );

    @POST("/registro/add")
    Call<String> agregarRegistro(
            @Body Registro registro
    );

    @GET("/registros/{id}")
    Call<RegistroResponse> obtenerRegistros(
            @Path("id") int id
    );


    @GET("/registro/{patente_vehiculo}")
    Call<List<RegistroTarifa>> obtenerRegistro(
            @Path("patente_vehiculo") String patente_vehiculo
    );


    @PUT("/registro/update/{idRegistro}")
    Call<String> actualizarRegistro(
            @Path("idRegistro") int idRegistro,
            @Body Registro registro
    );

    @PUT("/registro2/update/{idRegistro}")
    Call<String> actualizarRegistro2(
            @Path("idRegistro") int idRegistro
    );

    @DELETE("/registro/delete/{idRegistro}")
    Call<String> borrarRegistro(
            @Path("idRegistro") int idRegistro
    );

    //TARIFA

    @GET("/tarifas")
    Call<TarifaResponse> obtenerTarifas();

    @GET("/tarifa/{id}")
    Call<String> obtenerTarifa(
            @Path("id") int id
    );

    @POST("/tarifa/add")
    Call<String> agregarTarifa(
            @Body Tarifa tarifa
    );

    @PUT("/tarifa/update/{id}")
    Call<String> actualizarTarifa(
            @Path("id") int idUsuario,
            @Body Tarifa tarifa
    );

    @DELETE("/tarifa/delete/{id}")
    Call<String> borrarTarifa(
            @Path("id") int id
    );

    @GET("/espacios")
    Call<EspacioResponse> obtenerEspacios();

    @PUT("/espacio/update/{id}")
    Call<String> actualizarEspacio(
            @Path("id") int idEspacio,
            @Body Espacio espacio
    );

    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("/roles")
    Call<RolResponse> obtenerRoles();
}
