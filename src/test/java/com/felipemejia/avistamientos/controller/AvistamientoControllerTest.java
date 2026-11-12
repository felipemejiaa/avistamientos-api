package com.felipemejia.avistamientos.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.felipemejia.avistamientos.infraestructura.salida.AvistamientoEntidad;
import com.felipemejia.avistamientos.infraestructura.salida.AvistamientoRepositorioJpa;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:pruebas")
class AvistamientoControllerTest {

    @Autowired WebApplicationContext contexto;
    @Autowired AvistamientoRepositorioJpa repository;
    MockMvc mvc;

    private static final String VALIDO =
            "{\"especie\":\"Águila\",\"lugar\":\"Chingaza\",\"fecha\":\"2026-10-01\",\"observador\":\"Felipe\"}";

    @BeforeEach
    void preparar() {
        mvc = MockMvcBuilders.webAppContextSetup(contexto).build();
        repository.deleteAll();
    }

    private AvistamientoEntidad guardarUno() {
        AvistamientoEntidad e = new AvistamientoEntidad();
        e.setEspecie("Colibrí");
        e.setLugar("Usaquén");
        e.setFecha(LocalDate.of(2026, 9, 1));
        e.setObservador("Ana");
        return repository.save(e);
    }

    @Test
    void listar_responde200_conLosAvistamientosGuardados() throws Exception {
        guardarUno();
        mvc.perform(get("/avistamientos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].especie").value("Colibrí"));
    }

    @Test
    void obtener_responde200_cuandoElIdExiste() throws Exception {
        AvistamientoEntidad a = guardarUno();
        mvc.perform(get("/avistamientos/" + a.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lugar").value("Usaquén"));
    }

    @Test
    void obtener_responde404_cuandoElIdNoExiste() throws Exception {
        mvc.perform(get("/avistamientos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crear_responde201_yLoGuarda_cuandoEsValido() throws Exception {
        mvc.perform(post("/avistamientos").contentType(MediaType.APPLICATION_JSON).content(VALIDO))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.especie").value("Águila"))
                .andExpect(jsonPath("$.id").exists());
        assertEquals(1, repository.count());
    }

    @Test
    void crear_responde400_conMensaje_cuandoLaEspecieEsCorta() throws Exception {
        String json = VALIDO.replace("Águila", "ab");
        mvc.perform(post("/avistamientos").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("especie: debe tener entre 3 y 50 caracteres"));
        assertEquals(0, repository.count());
    }

    @Test
    void crear_responde400_conMensaje_cuandoLaFechaTieneMalFormato() throws Exception {
        String json = VALIDO.replace("2026-10-01", "hola");
        mvc.perform(post("/avistamientos").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("fecha: formato inválido, use AAAA-MM-DD"));
    }

    @Test
    void actualizar_responde200_yCambiaLosDatos_cuandoEsValido() throws Exception {
        AvistamientoEntidad a = guardarUno();
        mvc.perform(put("/avistamientos/" + a.getId()).contentType(MediaType.APPLICATION_JSON).content(VALIDO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.especie").value("Águila"));
        assertEquals("Águila", repository.findById(a.getId()).get().getEspecie());
    }

    @Test
    void actualizar_responde400_cuandoElLugarEstaVacio() throws Exception {
        AvistamientoEntidad a = guardarUno();
        String json = VALIDO.replace("Chingaza", "");
        mvc.perform(put("/avistamientos/" + a.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("lugar: no puede estar vacío"));
    }

    @Test
    void actualizar_responde404_cuandoElIdNoExiste() throws Exception {
        mvc.perform(put("/avistamientos/999").contentType(MediaType.APPLICATION_JSON).content(VALIDO))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_responde204_yLuegoGetDa404() throws Exception {
        AvistamientoEntidad a = guardarUno();
        mvc.perform(delete("/avistamientos/" + a.getId()))
                .andExpect(status().isNoContent());
        mvc.perform(get("/avistamientos/" + a.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_responde404_cuandoElIdNoExiste() throws Exception {
        mvc.perform(delete("/avistamientos/999"))
                .andExpect(status().isNotFound());
    }
}