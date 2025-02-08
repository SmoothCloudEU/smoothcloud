package eu.smoothcloud.node.group;

import eu.smoothcloud.api.service.IService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GroupHandler {
    private List<IService> services;
}
