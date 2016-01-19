/*
 * Copyright (c) 2011, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package sample;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


/**
 * Sample custom control hosting a text field and a button.
 */
public class SphereControl extends AnchorPane implements Command {

    private String controlName;

    public SphereControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sphere.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.controlName = "tool.circle";
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String getName() {
        return controlName;
    }

    @Override
    public void setAction(EventHandler<ActionEvent> value) {
        onActionProperty().set(value);
    }

    @Override
    public void execute(GraphicsContext gc, MouseEvent e, Color color, int size) {
        gc.setFill(color);

       if(e.getEventType()==MouseEvent.MOUSE_PRESSED) {
            gc.beginPath();
            gc.moveTo(e.getX(), e.getY());
            gc.fillOval(e.getX()-(size/2),e.getY()-(size/2),size,size);
            gc.stroke();
        }
    }

    public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty() { return onAction; }
    private ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase<EventHandler<ActionEvent>>() {
        @Override
        public Object getBean() {
            return SphereControl .this;
        }

        @Override
        public String getName() {
            return "onAction";
        }

        @Override protected void invalidated() {
            setEventHandler(ActionEvent.ACTION, get());
        }
    };
}
