/*
 * Copyright 2006 Niclas Hedhman.
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.ops4j.pax.gobilator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class Pipe
    implements Runnable
{

    private InputStream m_in;
    private OutputStream m_pipe;
    private Thread m_thread;
    private boolean m_loop;

    public Pipe( InputStream in, OutputStream pipe )
    {
        m_in = in;
        m_pipe = pipe;
    }

    public void start()
    {
        m_thread = new Thread( this );
        m_thread.start();
    }

    public void stop()
    {
        m_loop = false;
        m_thread.interrupt();
    }

    public void run()
    {
        m_loop = true;
        while( m_loop )
        {
            try
            {
                int ch = m_in.read();
                if( ch == -1 )
                {
                    break;
                }
                m_pipe.write( ch );
                m_pipe.flush();
            } catch( IOException e )
            {
                if( m_loop )
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
