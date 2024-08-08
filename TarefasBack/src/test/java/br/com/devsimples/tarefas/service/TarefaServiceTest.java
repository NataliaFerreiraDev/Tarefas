package br.com.devsimples.tarefas.service;

import br.com.devsimples.tarefas.entity.Tarefa;
import br.com.devsimples.tarefas.entity.TarefaDTO;
import br.com.devsimples.tarefas.mapper.TarefaMapper;
import br.com.devsimples.tarefas.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private TarefaMapper tarefaMapper;

    @InjectMocks
    private TarefaService tarefaService;

    private Tarefa tarefa;
    private TarefaDTO tarefaDTO;

    @BeforeEach
    void setUp() {
        tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setTitulo("Teste Tarefa");
        tarefa.setDescricao("Descrição de Teste");
        tarefa.setPrioridade("Alta");
        tarefa.setFinalizado(false);
        tarefa.setDataCriacao(LocalDateTime.now());
        tarefa.setDataConclusao(LocalDateTime.now());

        tarefaDTO = new TarefaDTO();
        tarefaDTO.setId(1L);
        tarefaDTO.setTitulo("Teste Tarefa");
        tarefaDTO.setDescricao("Descrição de Teste");
        tarefaDTO.setPrioridade("Alta");
        tarefaDTO.setFinalizado(false);
    }

    @Test
    void listarTodas_deveRetornarListaDeTarefas() {
        when(tarefaRepository.findAll()).thenReturn(List.of(tarefa));
        when(tarefaMapper.toDTO(any(Tarefa.class))).thenReturn(tarefaDTO);

        List<TarefaDTO> tarefas = tarefaService.listarTodas();

        assertEquals(1, tarefas.size());
        assertEquals(tarefaDTO.getTitulo(), tarefas.get(0).getTitulo());
        verify(tarefaRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_deveRetornarTarefaQuandoExistir() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));
        when(tarefaMapper.toDTO(any(Tarefa.class))).thenReturn(tarefaDTO);

        Optional<TarefaDTO> tarefaEncontrada = tarefaService.buscarPorId(1L);

        assertTrue(tarefaEncontrada.isPresent());
        assertEquals(tarefaDTO.getTitulo(), tarefaEncontrada.get().getTitulo());
        verify(tarefaRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_deveRetornarVazioQuandoNaoExistir() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<TarefaDTO> tarefaEncontrada = tarefaService.buscarPorId(1L);

        assertFalse(tarefaEncontrada.isPresent());
        verify(tarefaRepository, times(1)).findById(1L);
    }

    @Test
    void criarTarefa_deveSalvarENaoFinalizarTarefa() {
        when(tarefaMapper.toEntity(any(TarefaDTO.class))).thenReturn(tarefa);
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);
        when(tarefaMapper.toDTO(any(Tarefa.class))).thenReturn(tarefaDTO);

        TarefaDTO novaTarefa = tarefaService.criarTarefa(tarefaDTO);

        assertNotNull(novaTarefa);
        assertFalse(novaTarefa.isFinalizado());
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void atualizarTarefa_deveAtualizarQuandoExistir() {
        tarefaDTO.setFinalizado(true);
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);
        when(tarefaMapper.toDTO(any(Tarefa.class))).thenReturn(tarefaDTO);

        Optional<TarefaDTO> tarefaAtualizada = tarefaService.atualizarTarefa(1L, tarefaDTO);

        assertTrue(tarefaAtualizada.isPresent());
        assertEquals(tarefaDTO.getTitulo(), tarefaAtualizada.get().getTitulo());
        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void atualizarTarefa_deveRetornarVazioQuandoNaoExistir() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<TarefaDTO> tarefaAtualizada = tarefaService.atualizarTarefa(1L, tarefaDTO);

        assertFalse(tarefaAtualizada.isPresent());
        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, never()).save(any(Tarefa.class));
    }

    @Test
    void deletarTarefa_deveDeletarQuandoExistir() {
        when(tarefaRepository.existsById(anyLong())).thenReturn(true);

        boolean deletado = tarefaService.deletarTarefa(1L);

        assertTrue(deletado);
        verify(tarefaRepository, times(1)).existsById(1L);
        verify(tarefaRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletarTarefa_deveRetornarFalsoQuandoNaoExistir() {
        when(tarefaRepository.existsById(anyLong())).thenReturn(false);

        boolean deletado = tarefaService.deletarTarefa(1L);

        assertFalse(deletado);
        verify(tarefaRepository, times(1)).existsById(1L);
        verify(tarefaRepository, never()).deleteById(1L);
    }

    @Test
    void marcarComoFinalizado_deveAtualizarFinalizadoQuandoExistir() {
        LocalDateTime agora = LocalDateTime.now();

        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any(Tarefa.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(tarefaMapper.toDTO(any(Tarefa.class))).thenAnswer(invocation -> {
            Tarefa tarefaSalva = invocation.getArgument(0);
            tarefaDTO.setFinalizado(tarefaSalva.isFinalizado());
            tarefaDTO.setDataConclusao(agora);
            return tarefaDTO;
        });

        Optional<TarefaDTO> tarefaFinalizada = tarefaService.marcarComoFinalizado(1L);

        assertTrue(tarefaFinalizada.isPresent());
        assertTrue(tarefaFinalizada.get().isFinalizado(), "A tarefa deve estar marcada como finalizada");
        assertNotNull(tarefaFinalizada.get().getDataConclusao(), "A data de conclusão não deve ser nula");

        assertTrue(tarefaFinalizada.get().getDataConclusao().isEqual(agora));

        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void marcarComoFinalizado_deveRetornarVazioQuandoNaoExistir() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<TarefaDTO> tarefaFinalizada = tarefaService.marcarComoFinalizado(1L);

        assertFalse(tarefaFinalizada.isPresent());
        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, never()).save(any(Tarefa.class));
    }
}