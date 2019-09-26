package com.example.carros.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    private CarroRepository repository;

    public Iterable<Carro> getCarros(){
        return repository.findAll();
    }

    public Optional<Carro> getCarrosByID(Long id) {
        return repository.findById(id);
    }

    public Iterable<Carro> getCarrosByTipo(String tipo) {
        return repository.findByTipo(tipo);
    }

    public Carro insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível inserir o registro.");
        return repository.save(carro);
    }

    public Carro update(Carro carro, Long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro.");

        //Busca o carro no banco de dados
        Optional<Carro> optional = getCarrosByID(id);
        if(optional.isPresent()) {
            Carro db = optional.get();
            //Copiar as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id " + db.getId());

            //Atualiza o carro

            repository.save(db);

            return db;
        }else{
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public void delete(Long id) {
        Optional<Carro> carro = getCarrosByID(id);

        if(carro.isPresent()){
            repository.deleteById(id);
        }
    }
}