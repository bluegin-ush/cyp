package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.Item;
import blugin.com.ar.repository.ItemRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(paged = false)
public interface ItemResource extends PanacheRepositoryResource<ItemRepository, Item, Long> { }
