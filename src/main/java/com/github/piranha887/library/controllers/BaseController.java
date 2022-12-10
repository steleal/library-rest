package com.github.piranha887.library.controllers;

import java.util.List;

abstract class BaseController<E> {

    abstract public List<E> getAll();

    abstract public E getById(long id);

    abstract public long add(E dto);

    abstract public void update(long id, E dto);

    abstract public void delete(long id);

}
