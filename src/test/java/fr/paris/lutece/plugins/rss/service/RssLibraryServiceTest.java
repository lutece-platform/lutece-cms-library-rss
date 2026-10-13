/*
 * Copyright (c) 2002-2026, Mairie de Paris
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.paris.lutece.test.ReflectionTestUtils;
import jakarta.enterprise.inject.Instance;

/**
 * RssLibraryServiceTest
 */
public class RssLibraryServiceTest
{
    private RssLibraryService _rssLibraryService;

    /**
     * Builds the service with no RSS service deployed.
     */
    @BeforeEach
    public void setUp( )
    {
        _rssLibraryService = new RssLibraryService( );
        ReflectionTestUtils.setField( _rssLibraryService, "_service", unresolvableInstance( ) );
    }

    /**
     * The service is unavailable when no plugin provides an RSS service.
     */
    @Test
    public void testIsAvailableWithoutRssService( )
    {
        assertFalse( _rssLibraryService.isAvailable( ), "no RSS service deployed" );
    }

    /**
     * The RSS content is null when no plugin provides an RSS service.
     */
    @Test
    public void testGetRssContentWithoutRssService( )
    {
        assertNull( _rssLibraryService.getRssContent( 1 ), "no RSS content without RSS service" );
    }

    /**
     * The feed list is null when no plugin provides an RSS service.
     */
    @Test
    public void testGetRefListRssFeedWithoutRssService( )
    {
        assertNull( _rssLibraryService.getRefListRssFeed( ), "no feed list without RSS service" );
    }

    /**
     * Returns an instance that resolves no bean and fails on any other call.
     *
     * @return the unresolvable instance
     */
    @SuppressWarnings( "unchecked" )
    private static Instance<IRssService> unresolvableInstance( )
    {
        return (Instance<IRssService>) Proxy.newProxyInstance( RssLibraryServiceTest.class.getClassLoader( ), new Class<?>[] { Instance.class },
                ( proxy, method, args ) -> {
                    if ( "isResolvable".equals( method.getName( ) ) )
                    {
                        return false;
                    }
                    throw new UnsupportedOperationException( method.getName( ) );
                } );
    }
}
