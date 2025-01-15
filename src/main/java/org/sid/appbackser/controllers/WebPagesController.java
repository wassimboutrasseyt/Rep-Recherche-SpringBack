package org.sid.appbackser.controllers;

import java.io.File;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sid.appbackser.entities.Project;
import org.sid.appbackser.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web")
public class WebPagesController {
    @Autowired
	private ProjectService projectService;

    
    @GetMapping("/{projectName}")
    public ResponseEntity<?> serveProjectDepot(@PathVariable String projectName) {
        // Retrieve the project using its short name
        Project project = projectService.getProjectByShortName(projectName);
    
        if (project == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found.");
        }
    
        // Path to the project depot
        String depotFolderPath = project.getRessourceProject().getWebDepot().getLocalPath() + "/Host/";
        File indexFile = new File(Paths.get(depotFolderPath, "index.html").toString());

        if (!indexFile.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The requested project depot does not contain an index.html file.");
        }
    
        try {
            // Read the HTML file content
            String htmlContent = new String(java.nio.file.Files.readAllBytes(indexFile.toPath()));
    
            // Embed linked CSS
            htmlContent = embedAssets(htmlContent, "link", "href", depotFolderPath);
    
            // Embed linked JS
            htmlContent = embedAssets(htmlContent, "script", "src", depotFolderPath);
    
            return ResponseEntity.ok()
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .body(htmlContent);
    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process the requested file.");
        }
    }
    
    private String embedAssets(String htmlContent, String tagName, String attributeName, String baseFolder) {
        // Match tags like <tagName attributeName="filePath">
        String regex = "<" + tagName + ".*?" + attributeName + "=\"(.*?)\".*?>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlContent);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String assetPath = matcher.group(1);
            File assetFile = new File(Paths.get(baseFolder, assetPath).toString());
            String replacement;
            if (assetFile.exists()) {
                try {
                    String assetContent = new String(java.nio.file.Files.readAllBytes(assetFile.toPath()));
                    if ("link".equals(tagName)) {
                        replacement = "<style>" + assetContent + "</style>";
                    } else if ("script".equals(tagName)) {
                        replacement = "<script>" + assetContent + "</script>";
                    } else {
                        replacement = matcher.group(0);
                    }
                } catch (Exception e) {
                    replacement = matcher.group(0);
                }
            } else {
                replacement = matcher.group(0);
            }
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);
        return result.toString();
    }
    
}
