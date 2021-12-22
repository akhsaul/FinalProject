package org.kelompok3.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ServerError implements ErrorController {
    private final Logger LOGGER = LoggerFactory.getLogger(ServerError.class);

    @RequestMapping("/error")
    public ResponseEntity<?> handleError(HttpServletRequest request) {
        var url = request.getHeader("Host") + request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
        LOGGER.error("ERROR. " + request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) + ". " + url);
        return new ResponseEntity<>("ERROR. " + url, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getRequestDispatcher("/error").forward(req, resp);
    }
}