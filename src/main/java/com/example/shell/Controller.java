package com.example.shell;

import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;

@RestController
@RequestMapping("/api/shell")
public class Controller {

    @PostMapping("")
    private String test(@RequestBody ShellCommand shellCommand) throws InterruptedException, IOException {

        String path = shellCommand.getPath();
        path = path.replaceAll("/", Matcher.quoteReplacement("\\"));

        Process process = Runtime.getRuntime().exec(
                String.format("cmd.exe /c %s", shellCommand.getCommand()),
                null,
                new File(path)
        );
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
        }

        int exitVal = process.waitFor();
        if (exitVal == 0) System.out.println(output);
        else System.out.println("error");
        return "shell execute";
    }
}
