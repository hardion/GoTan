/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gotan.model.domain

/**
 *
 * @author vinhar
 */
interface ClassResolver {

    def newInstance(def classname)
}

