/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.rss.service;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.ReferenceList;

/**
 * RssLibraryService
 */
public class RssLibraryService
{
    private static final String PLUGIN_RSS_NAME = "rss";
    private static RssLibraryService _singleton;
    private boolean _bServiceAvailable = true;
    private IRssService _service;

    /**
     * Private constructor
     */
    private RssLibraryService(  )
    {
        try
        {
            _service = (IRssService) SpringContextService.getPluginBean( PLUGIN_RSS_NAME, "rssService" );
            _bServiceAvailable = _service != null;
        }
        catch ( BeanDefinitionStoreException e )
        {
            _bServiceAvailable = false;
            AppLogService.error( PLUGIN_RSS_NAME + " unavailable" );
        }
        catch ( NoSuchBeanDefinitionException e )
        {
            _bServiceAvailable = false;
            AppLogService.error( PLUGIN_RSS_NAME + " unavailable" );
        }
        catch ( CannotLoadBeanClassException e )
        {
            _bServiceAvailable = false;
            AppLogService.error( PLUGIN_RSS_NAME + " unavailable" );
        }
    }

    /**
    * Returns the unique instance of the service
    * @return The instance of the service
    */
    public static RssLibraryService getInstance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new RssLibraryService(  );
        }

        return _singleton;
    }

    /**
    *
    * @return true if the workflow service is available
    */
    public boolean isAvailable(  )
    {
        return _bServiceAvailable && PluginService.isPluginEnable( PLUGIN_RSS_NAME );
    }

    /**
     * Return the RSS content
     * @param nIdRssFeed the id of rss feed
     * @return the RSS content
     */
    public String getRssContent( int nIdRssFeed )
    {
        return isAvailable(  ) ? _service.getRssContent( nIdRssFeed ) : null;
    }

    /**
     * Return the Reference List of Rss Feed
     * @return the Reference List of Rss Feed
     */
    public ReferenceList getRefListRssFeed(  )
    {
        return isAvailable(  ) ? _service.getRefListRssFeed(  ) : null;
    }
}
