package com.application.jobportalbackend.dto;

import com.application.jobportalbackend.entity.Status;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JobStatusUpdateDTO {

    private Status jobStatus;
}
