package br.com.devsimples.tarefas.controller;

import br.com.devsimples.tarefas.entity.TarefaDTO;
import br.com.devsimples.tarefas.service.TarefaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public List<TarefaDTO> listarTodos() {
        return tarefaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> buscarPorId(@PathVariable Long id) {
        Optional<TarefaDTO> tarefaDTO = tarefaService.buscarPorId(id);
        return tarefaDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TarefaDTO> criarTarefa(@Valid @RequestBody TarefaDTO tarefaDTO) {
        TarefaDTO novaTarefaDTO = tarefaService.criarTarefa(tarefaDTO);
        return ResponseEntity.ok(novaTarefaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTO> atualizarTarefa(@PathVariable Long id, @Valid @RequestBody TarefaDTO todoDTO) {
        Optional<TarefaDTO> updatedTodoDTO = tarefaService.atualizarTarefa(id, todoDTO);
        return updatedTodoDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        boolean deletado = tarefaService.deletarTarefa(id);
        return deletado ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<TarefaDTO> marcarComoFinalizado(@PathVariable Long id) {
        Optional<TarefaDTO> todoDTO = tarefaService.marcarComoFinalizado(id);
        return todoDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
