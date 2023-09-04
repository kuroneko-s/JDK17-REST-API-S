package com.example.restapi.automatic.sample;

import org.springframework.stereotype.Service;

@Service
public interface SampleService {
    void sampleMethod(SampleMethodParams params);
}

class SampleMethodParams {

}