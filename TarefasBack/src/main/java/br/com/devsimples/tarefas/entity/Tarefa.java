package br.com.devsimples.tarefas.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tarefas")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String prioridade;

    private boolean finalizado;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataUltimaAtualizacao;

    private LocalDateTime dataConclusao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataUltimaAtualizacao = LocalDateTime.now();
    }

}
