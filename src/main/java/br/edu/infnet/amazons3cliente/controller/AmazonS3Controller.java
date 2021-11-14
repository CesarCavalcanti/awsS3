package br.edu.infnet.amazons3cliente.controller;

import br.edu.infnet.amazons3cliente.service.AmazonClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("amazon")
public class AmazonS3Controller {

    @Autowired
    private AmazonClienteService amazonClienteService;

    @GetMapping(value = "upload")
    public String seePage(){
        return "uploadFile.html";
    }

    @PostMapping(value = "upload")
    public String uploadFile (@RequestPart(value = "file")MultipartFile file){
        amazonClienteService.save(file);
        return "redirect:/amazon/upload";
    }
    @GetMapping(value = "list")
    public String seeListPage (Model model){
        List<String> arquivos = amazonClienteService.listar();
        model.addAttribute("arquivos",arquivos);
        return "list.html";
    }

    @GetMapping(value = "download/{fileName}")
    public HttpEntity<byte[]> downloadFile(@PathVariable("fileName") String fileName) throws IOException {
        File file = amazonClienteService.download(fileName);
        byte[] bytes = Files.readAllBytes(file.toPath());
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        header.setContentLength(bytes.length);
        header.set(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + fileName.replace("","_"));
        return new HttpEntity<byte[]>(bytes,header);
    }

    @GetMapping(value = "delete/{fileName}")
    public String delete(@PathVariable("fileName") String fileName){
        amazonClienteService.delete(fileName);
        return "redirect:/amazon/list";
    }
}
