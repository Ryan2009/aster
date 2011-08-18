/*
 * Copyright (C) 2011 0xlab - http://0xlab.org/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authored by Kan-Ru Chen <kanru@0xlab.org>
 */

package org.zeroxlab.aster;

import javax.swing.*;

import java.io.IOException;

import java.lang.ArrayIndexOutOfBoundsException;

public class AsterMain {

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                if ("-run".equals(args[0])) {
                    startCLI(args[1]);
                } else if ("-suite".equals(args[0])) {
                    startSuiteRunner(args[1]);
                } else {
                    usage();
                }
            } catch(ArrayIndexOutOfBoundsException e) {
                usage();
            }
        } else {
            startGUI();
        }
    }

    public static void startCLI(String script) {
        try {
            (new AsterCommandManager()).run(script);
        } catch(IOException e) {
            System.err.printf(e.toString());
            System.exit(1);
        }
        System.exit(0);
    }

    public static void startGUI() {
        trySetupLookFeel();
        JFrame f = new JFrame("Aster");
        AsterMainPanel p = new AsterMainPanel();
        f.setContentPane(p);
        f.setJMenuBar(p.createMenuBar());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

    public static void startSuiteRunner(String directory) {
        AsterSuiteRunner.run(directory);
    }

    private static void trySetupLookFeel() {
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                "com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }
    }

    private static void usage() {
        System.out.printf("Usage: aster [[-run TEST.ast][-suite DIRECTORY]]\n\n");
    }
}
