package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    //변경 감지 기능 사용 ==> 이게 정석이다.
    // 트랜잭션이 있는 서비스 계층에 식별자와 변경할 데이터를 명확하게 전달
    // 트랜잭션이 있는 서비스 계층에서
    // itemRepository.findOne(itemId); 와 같이 영속 상태의 엔티티르 조회하고
    // 엔티티의 데이터를 직접 변경해야
    // 트랜잭션 커밋시점에서 변경감지가 실행된다.
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        //findItem은 영속상태의 객체이다.
        //따라서 값을 set하면 jpa가 flush하면서 변경지점을 찾고 update를 쳐버린다.
      Item findItem = itemRepository.findOne(itemId);
      findItem.setName(name);
      findItem.setPrice(price);
      findItem.setStockQuantity(stockQuantity);

    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
