package br.com.devsimples.tarefas.service;

import br.com.devsimples.tarefas.entity.Tarefa;
import br.com.devsimples.tarefas.entity.TarefaDTO;
import br.com.devsimples.tarefas.mapper.TarefaMapper;
import br.com.devsimples.tarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaMapper tarefaMapper;

    public TarefaService(TarefaRepository tarefaRepository, TarefaMapper tarefaMapper) {
        this.tarefaRepository = tarefaRepository;
        this.tarefaMapper = tarefaMapper;
    }


    public List<TarefaDTO> listarTodas() {
        return tarefaRepository.findAll().stream()
                .map(tarefaMapper::toDTO)
                .toList();
    }

    public Optional<TarefaDTO> buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .map(tarefaMapper::toDTO);
    }

    public TarefaDTO criarTarefa(TarefaDTO tarefaDTO) {
        tarefaDTO.setFinalizado(false);
        Tarefa tarefa = tarefaMapper.toEntity(tarefaDTO);
        Tarefa novaTarefa = tarefaRepository.save(tarefa);
        return tarefaMapper.toDTO(novaTarefa);
    }

    public Optional<TarefaDTO> atualizarTarefa(Long id, TarefaDTO tarefaDTO) {
        return tarefaRepository.findById(id).map(tarefaExistente -> {
            tarefaExistente.setTitulo(tarefaDTO.getTitulo());
            tarefaExistente.setDescricao(tarefaDTO.getDescricao());
            tarefaExistente.setPrioridade(tarefaDTO.getPrioridade());
            tarefaExistente.setFinalizado(tarefaDTO.isFinalizado());
            if (tarefaDTO.isFinalizado()) {
                tarefaExistente.setDataConclusao(LocalDateTime.now());
            }
            Tarefa tarefaAtualizada = tarefaRepository.save(tarefaExistente);
            return tarefaMapper.toDTO(tarefaAtualizada);
        });
    }

    public boolean deletarTarefa(Long id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<TarefaDTO> marcarComoFinalizado(Long id) {
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefa.setFinalizado(true);
            tarefa.setDataConclusao(LocalDateTime.now());
            Tarefa tarefaAtualizada = tarefaRepository.save(tarefa);
            return tarefaMapper.toDTO(tarefaAtualizada);
        });
    }

}
