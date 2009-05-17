package ginkgo.webapp;

import ginkgo.api.VcsPlugin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PluginRepository {

    private Map<String, VcsPlugin> _map = new HashMap<String, VcsPlugin>();

    @Autowired
    public PluginRepository(VcsPlugin... vcsPlugins) {
        for (VcsPlugin plugin : vcsPlugins) {
            String name = plugin.getName();
            _map.put(name, plugin);
        }
    }

    public VcsPlugin create(String name) {
        VcsPlugin vcsPlugin = _map.get(name);
        VcsPlugin newInstance = null;
        try {
            newInstance = vcsPlugin.getClass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newInstance;
    }

    public boolean exists(String name) {
        return _map.containsKey(name);
    }
}
