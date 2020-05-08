/*
 * Copyright (c) 2020 Diego Urrutia-Astorga. http://durrutia.cl.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package cl.ucn.disc.pdis.lab.services;

import cl.ucn.disc.pdis.lab.zeroice.model.Engine;
import com.zeroc.Ice.Current;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The implementation of {@link cl.ucn.disc.pdis.lab.zeroice.model.Engine}.
 *
 * @author Diego Urrutia-Astorga.
 */
public final class EngineImpl implements Engine {

    /**
     * @see Engine#getDate(Current)
     */
    @Override
    public String getDate(Current current) {
        return ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public String getDigitoVerificador(String rut, Current current) {

        String correctDigit;
        int totalDigits = rut.length();

        /*
        Se verifica si tiene guión
         */
        int startPosition;
        if(rut.charAt(totalDigits - 2) == '-'){
            startPosition = 3;
        }else{
            startPosition = 2;
        }

        /*
        Suma de dígitos de atrás para adelante multiplicados por números del 2 al 7
        dependiendo el orden
         */
        int result1 = 0;
        int multiplier = 2;
        for(int i = totalDigits - startPosition; i >= 0; i--){
            result1 = result1 + (multiplier * Integer.parseInt(String.valueOf(rut.charAt(i))));
            if(multiplier == 7){
                multiplier = 2;
            }else{
                multiplier++;
            }
        }

        /*
        El resultado se divide entre 11 y se conserva sólo la parte entera sin aproximar
         */
        int result2 = result1 / 11;

        /*
        Luego se multiplica por 11
         */
        int result3 = result2 * 11;

        /*
        Ahora a 11 se le resta la diferencia entre la primera suma y la multiplicación
        de la división sin aproximar
         */
        int result4 = 11 - (result1 - result3);

        /*
        Si el resultado es 11 se entrega "0", si es 10 se entrega "K" y
        si no, se entrega el resultado
         */
        if(result4 == 11){
            correctDigit = "0";
        }
        if(result4 == 10){
            correctDigit = "K";
        }else{
            correctDigit = Integer.toString(result4);
        }

        /*
        Retornar el dígito verificador si es correcto y vacío si no lo es
         */
        if(correctDigit.equalsIgnoreCase(String.valueOf(rut.charAt(totalDigits - 1)))){
            return correctDigit;
        }else{
            return null;
        }
    }
}
