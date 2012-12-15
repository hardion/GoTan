/**
 * Copyright (C) :     2012
 *
 * 	Synchrotron Soleil
 * 	L'Orme des merisiers
 * 	Saint Aubin
 * 	BP48
 * 	91192 GIF-SUR-YVETTE CEDEX
 *
 * This file is part of Tango.
 *
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gotan.tango;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.tango.server.ServerManager;
import org.tango.server.servant.Constants;
import org.tango.server.testserver.JTangoTest;

import fr.esrf.Tango.DevFailed;

/**
 * Starts, stop the server JTangoTest without tango db
 * 
 * @author ABEILLE
 * 
 */
public class NoDBDeviceManager {

    public static String deviceName;

    public static String adminName;

    @BeforeClass
    public static void startDevice(String devicename) throws DevFailed, IOException {
	ServerSocket ss1 = null;
	try {
	    ss1 = new ServerSocket(0);
	    ss1.setReuseAddress(true);
	    ss1.close();
	    //JTangoTest.startNoDb(ss1.getLocalPort());
            // From JTangoTest.startNoDb with the possibility to change the name
            System.setProperty("OAPort", Integer.toString(ss1.getLocalPort()));
            ServerManager.getInstance().addClass(JTangoTest.class.getCanonicalName(), JTangoTest.class);
            ServerManager.getInstance().startError(new String[] { "test", "-nodb", "-dlist", devicename },
                    JTangoTest.class.getSimpleName());

	    deviceName = "tango://localhost:" + ss1.getLocalPort() + "/" + devicename + "#dbase=no";
	    adminName = "tango://localhost:" + ss1.getLocalPort() + "/" + Constants.ADMIN_DEVICE_DOMAIN + "/"
		    + ServerManager.getInstance().getServerName() + "#dbase=no";
	} finally {
	    if (ss1 != null)
		ss1.close();
	}

    }

    @AfterClass
    public static void stopDevice() throws DevFailed {
	ServerManager.getInstance().stop();
    }

}
