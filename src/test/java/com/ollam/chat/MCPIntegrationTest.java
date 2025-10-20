package com.ollam.chat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MCPIntegration
 */
public class MCPIntegrationTest {
    
    private MCPIntegration mcpIntegration;
    
    @BeforeEach
    public void setUp() {
        mcpIntegration = new MCPIntegration();
    }
    
    @Test
    public void testMCPIntegrationInitialization() {
        assertNotNull(mcpIntegration, "MCP Integration should be initialized");
        assertNotNull(mcpIntegration.getDynatraceServer(), "Dynatrace server should be initialized");
        assertNotNull(mcpIntegration.getGithubServer(), "Github server should be initialized");
    }
    
    @Test
    public void testGetStatus() {
        String status = mcpIntegration.getStatus();
        assertNotNull(status, "Status should not be null");
        assertTrue(status.contains("Dynatrace MCP"), "Status should contain Dynatrace MCP info");
        assertTrue(status.contains("Github MCP"), "Status should contain Github MCP info");
    }
    
    @Test
    public void testDynatraceServerStatus() {
        MCPIntegration.DynatraceMCPServer dynatraceServer = mcpIntegration.getDynatraceServer();
        String status = dynatraceServer.getStatus();
        assertNotNull(status, "Dynatrace status should not be null");
    }
    
    @Test
    public void testGithubServerStatus() {
        MCPIntegration.GithubMCPServer githubServer = mcpIntegration.getGithubServer();
        String status = githubServer.getStatus();
        assertNotNull(status, "Github status should not be null");
    }
}
