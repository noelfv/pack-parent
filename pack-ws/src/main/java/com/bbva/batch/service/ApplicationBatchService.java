package com.bbva.batch.service;

import java.util.List;

import com.bbva.batch.domain.ApplicationBatch;

public interface ApplicationBatchService {

    List<ApplicationBatch> listar();

    List<ApplicationBatch> listar(boolean lazy);

    ApplicationBatch obtener(Long idApplication);

    ApplicationBatch obtener(Long idApplication, boolean lazy);

    void insertar(ApplicationBatch o);

    void actualizar(ApplicationBatch o);

    void eliminar(ApplicationBatch o);
}
