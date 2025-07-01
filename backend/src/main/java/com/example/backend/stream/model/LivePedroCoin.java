package com.example.backend.stream.model;


import jakarta.persistence.*;

@Entity
public class LivePedroCoin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "streamId", referencedColumnName = "id")
    private Stream stream;

    private int coinsValue;

    public LivePedroCoin(){}
    public LivePedroCoin(Stream stream, int coinsValue) {
        this.stream = stream;
        this.coinsValue = coinsValue;
    }

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }

    public int getCoinsValue() {
        return coinsValue;
    }

    public void setCoinsValue(int coinsValue) {
        this.coinsValue = coinsValue;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void donate(int value){
        this.coinsValue = this.coinsValue + value;
    }
}
