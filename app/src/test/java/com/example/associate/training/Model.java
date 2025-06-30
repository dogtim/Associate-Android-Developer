package com.example.associate.training;
import java.util.Objects;

public class Model {
    private String mUrl;

    public Model(String url) {
        mUrl = url;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model that = (Model) o;
        return Objects.equals(mUrl, that.mUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mUrl);
    }

}