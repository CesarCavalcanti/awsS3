package br.edu.infnet.amazons3cliente.controller;

import br.edu.infnet.amazons3cliente.service.AmazonClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("storage")
public class AmazonS3RestController {

    @Autowired
    private AmazonClienteService amazonClienteService;

    @GetMapping
    public List<String> listar(){
        return amazonClienteService.listar();
    }
    @PostMapping("upload")
    public String uploadFile(@RequestPart (value = "file")MultipartFile file){
        amazonClienteService.save(file);
        return "Arquivo" + file.getName() + "salvo com sucesso";
    }
    @DeleteMapping("delete")
    public String deleteFile (@RequestParam("fileName") String fileName){
        amazonClienteService.delete(fileName);
        return "Arquivo" + fileName + "Apagado com sucesso";
    }
}
