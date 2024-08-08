package br.com.devsimples.tarefas.controller;

import br.com.devsimples.tarefas.entity.TarefaDTO;
import br.com.devsimples.tarefas.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Listar todas as tarefas")
    @GetMapping
    public List<TarefaDTO> listarTodos() {
        return tarefaService.listarTodas();
    }

    @Operation(summary = "Buscar uma tarefa por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> buscarPorId(@PathVariable Long id) {
        Optional<TarefaDTO> tarefaDTO = tarefaService.buscarPorId(id);
        return tarefaDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar uma nova tarefa")
    @PostMapping
    public ResponseEntity<TarefaDTO> criarTarefa(@Valid @RequestBody TarefaDTO tarefaDTO) {
        TarefaDTO novaTarefaDTO = tarefaService.criarTarefa(tarefaDTO);
        return ResponseEntity.ok(novaTarefaDTO);
    }

    @Operation(summary = "Atualizar uma tarefa existente")
    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTO> atualizarTarefa(@PathVariable Long id, @Valid @RequestBody TarefaDTO todoDTO) {
        Optional<TarefaDTO> updatedTodoDTO = tarefaService.atualizarTarefa(id, todoDTO);
        return updatedTodoDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar uma tarefa por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        boolean deletado = tarefaService.deletarTarefa(id);
        return deletado ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Marcar uma tarefa como finalizada")
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<TarefaDTO> marcarComoFinalizado(@PathVariable Long id) {
        Optional<TarefaDTO> todoDTO = tarefaService.marcarComoFinalizado(id);
        return todoDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
