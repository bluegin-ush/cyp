package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.Item;
import blugin.com.ar.repository.ItemRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;

public interface ItemResource extends PanacheRepositoryResource<ItemRepository, Item, Long> { }
