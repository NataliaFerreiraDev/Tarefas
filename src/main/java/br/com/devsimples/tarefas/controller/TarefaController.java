package br.com.devsimples.tarefas.controller;

import br.com.devsimples.tarefas.entity.Tarefa;
import br.com.devsimples.tarefas.service.TarefaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping()
    public ResponseEntity<List<Tarefa>> listarTodas(){
        return ResponseEntity.ok(tarefaService.listar());
    }

    @PostMapping()
    public ResponseEntity<List<Tarefa>> salvar(@RequestBody Tarefa tarefa){
        tarefaService.criar(tarefa);

        return ResponseEntity.ok(tarefaService.listar());
    }

}
