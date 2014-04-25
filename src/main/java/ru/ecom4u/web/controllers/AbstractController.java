package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ecom4u.web.services.GlobalModelService;

/**
 * Created by Evgeny on 25.04.14.
 */
public abstract class AbstractController {
    @Autowired
    protected GlobalModelService globalModelService;
}
