package com.pulsus.pulsusbackend.service;

@Service
public class FIleService {

    @Value("${file.upload-dir}")
    private String dir;
}
