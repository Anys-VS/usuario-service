package com.ecommerce.usuario_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.usuario_service.dto.UsuarioRequest;
import com.ecommerce.usuario_service.dto.UsuarioResponse;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //Crear
    @PostMapping("path")
    public ResponseEntity<UsuarioResponse> crear(@Valid@RequestBody UsuarioRequest request){
        return ResponseEntity.ok(usuarioService.crearUusario(request));
    }

    //Listar
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar(){
        return ResponseEntity.ok(usuarioService.listar());
    }

    //Obtener por ID 
    @GetMapping("/{ID}")
    public ResponseEntity<UsuarioResponse> obtener(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }
    
    
}
