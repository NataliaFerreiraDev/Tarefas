package br.com.devsimples.tarefas.controller;

import br.com.devsimples.tarefas.entity.TarefaDTO;
import br.com.devsimples.tarefas.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaControllerTest {

    @Mock
    private TarefaService tarefaService;

    @InjectMocks
    private TarefaController tarefaController;

    private TarefaDTO tarefaDTO;

    @BeforeEach
    void setUp() {
        tarefaDTO = new TarefaDTO();
        tarefaDTO.setId(1L);
        tarefaDTO.setTitulo("Teste Tarefa");
        tarefaDTO.setDescricao("Descrição de Teste");
        tarefaDTO.setPrioridade("Alta");
        tarefaDTO.setFinalizado(false);
    }

    @Test
    void listarTodos_deveRetornarListaDeTarefas() {
        when(tarefaService.listarTodas()).thenReturn(List.of(tarefaDTO));

        List<TarefaDTO> tarefas = tarefaController.listarTodos();

        assertEquals(1, tarefas.size());
        assertEquals(tarefaDTO.getTitulo(), tarefas.get(0).getTitulo());
        verify(tarefaService, times(1)).listarTodas();
    }

    @Test
    void buscarPorId_deveRetornarTarefaQuandoExistir() {
        when(tarefaService.buscarPorId(anyLong())).thenReturn(Optional.of(tarefaDTO));

        ResponseEntity<TarefaDTO> response = tarefaController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tarefaDTO, response.getBody());
        verify(tarefaService, times(1)).buscarPorId(1L);
    }

    @Test
    void buscarPorId_deveRetornarNotFoundQuandoNaoExistir() {
        when(tarefaService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<TarefaDTO> response = tarefaController.buscarPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(tarefaService, times(1)).buscarPorId(1L);
    }

    @Test
    void criarTarefa_deveRetornarTarefaCriada() {
        when(tarefaService.criarTarefa(any(TarefaDTO.class))).thenReturn(tarefaDTO);

        ResponseEntity<TarefaDTO> response = tarefaController.criarTarefa(tarefaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tarefaDTO, response.getBody());
        verify(tarefaService, times(1)).criarTarefa(any(TarefaDTO.class));
    }

    @Test
    void atualizarTarefa_deveRetornarTarefaAtualizadaQuandoExistir() {
        when(tarefaService.atualizarTarefa(anyLong(), any(TarefaDTO.class))).thenReturn(Optional.of(tarefaDTO));

        ResponseEntity<TarefaDTO> response = tarefaController.atualizarTarefa(1L, tarefaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tarefaDTO, response.getBody());
        verify(tarefaService, times(1)).atualizarTarefa(anyLong(), any(TarefaDTO.class));
    }

    @Test
    void atualizarTarefa_deveRetornarNotFoundQuandoNaoExistir() {
        when(tarefaService.atualizarTarefa(anyLong(), any(TarefaDTO.class))).thenReturn(Optional.empty());

        ResponseEntity<TarefaDTO> response = tarefaController.atualizarTarefa(1L, tarefaDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(tarefaService, times(1)).atualizarTarefa(anyLong(), any(TarefaDTO.class));
    }

    @Test
    void deletarTarefa_deveRetornarOkQuandoDeletado() {
        when(tarefaService.deletarTarefa(anyLong())).thenReturn(true);

        ResponseEntity<Void> response = tarefaController.deletarTarefa(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(tarefaService, times(1)).deletarTarefa(1L);
    }

    @Test
    void deletarTarefa_deveRetornarNotFoundQuandoNaoExistir() {
        when(tarefaService.deletarTarefa(anyLong())).thenReturn(false);

        ResponseEntity<Void> response = tarefaController.deletarTarefa(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(tarefaService, times(1)).deletarTarefa(1L);
    }

    @Test
    void marcarComoFinalizado_deveRetornarTarefaFinalizadaQuandoExistir() {
        when(tarefaService.marcarComoFinalizado(anyLong())).thenReturn(Optional.of(tarefaDTO));

        ResponseEntity<TarefaDTO> response = tarefaController.marcarComoFinalizado(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tarefaDTO, response.getBody());
        verify(tarefaService, times(1)).marcarComoFinalizado(1L);
    }

    @Test
    void marcarComoFinalizado_deveRetornarNotFoundQuandoNaoExistir() {
        when(tarefaService.marcarComoFinalizado(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<TarefaDTO> response = tarefaController.marcarComoFinalizado(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(tarefaService, times(1)).marcarComoFinalizado(1L);
    }
}