package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.InquiryForm;
import com.example.demo.models.InquiryForm3;
import com.example.demo.models.ItemForm;
import com.example.demo.models.ItemEntity;
import com.example.demo.repositries.InquiryRepository;
import com.example.demo.repositries.InquiryRepository3;
import com.example.demo.repositries.ItemRepository;
import com.example.demo.services.ItemService;

import org.springframework.web.bind.annotation.ResponseBody;

//import antlr.collections.List;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/item")
public class ItemController {

//	//InquiryRepository repository = new InquiryRepository()
//	@Autowired//アノテーション
//	InquiryRepository repository;
//	//インポートしないと定義されない
//	@Autowired
//	InquiryRepository3 repository3;

//	@Autowired
//	ItemRepository itemrepository;
	
	@Autowired
    private ItemService itemService1;
	
	// 司書さん（Service）を呼べるように追加
//	@Autowired
//    private com.example.demo.services.ItemService itemService;
	
	
	// --- 新規登録画面の表示 ---
	@GetMapping("/create")
	public String create(ItemForm itemForm) {
		return "item/create";
	}
	
	// --- 商品一覧の表示 ---
	@GetMapping("/list")
	// Serviceに「画面表示用のFormリスト」をもらう
	public String list(Model model) {
		// Serviceからリストを受け取る
	    List<ItemEntity> itemlists = itemService1.findAll();
	    // modelに入れてHTMLへ渡す
	    model.addAttribute("itemlists", itemlists);
		return "item/list";
	}
	
//	@PostMapping("/itemlists/{id}/favorite")
//	@ResponseBody
//	public boolean toggleFavorite(@PathVariable Long id) {
//	    ItemForm item = itemrepository.findById(id).get();
//	    item.setFavorite(!item.isFavorite()); // 反転
//	    itemrepository.save(item);
//	    
//	    return item.isFavorite(); // 「今の正解」を返してあげる
//	}
	
	// --- お気に入り状態（非同期通信用） ---
	@PostMapping("/itemlists/{id}/favorite")
    @ResponseBody
    public boolean toggleFavorite(@PathVariable Long id) {
        // 今までここで「取得・反転・保存」をしていた処理を
        // Serviceのメソッド1行に丸投げ
        return itemService1.toggleFavorite(id);
    }
	
//    @GetMapping("itemlists/{id}/edit")
//    public String edit(@PathVariable Long id, Model model) { 
//	    ItemForm itemForm = itemrepository.findById(id).get();
//	      model.addAttribute("itemForm", itemForm);
//	      return "item/edit";	
//    }
	
	// --- 編集画面の表示 ---
    @GetMapping("itemlists/{id}/edit")
    public String edit(@PathVariable Long id, Model model) { 
        // Serviceに丸投げして、DBから1件取ってもらう
        Optional<ItemEntity> itemEntity = itemService1.findById(id);
        
        // modelに入れてHTMLへ渡す
        model.addAttribute("itemForm", itemEntity.get());
        
        return "item/edit";	
        }
    
//    @GetMapping("itemlists/{id}/delete")
//   public String delete(@PathVariable Long id, Model model) { 
//    ItemForm itemForm = itemrepository.findById(id).get();
//       model.addAttribute("itemForm", itemForm);
//       model.addAttribute("message", "削除しますか？");
//        return "item/delete";
//    }
    
 // 削除確認画面を表示するための GET メソッド
    @GetMapping("itemlists/{id}/delete")
    public String deleteConfirm(@PathVariable Long id, Model model) { 
        Optional<ItemEntity> itemEntity = itemService1.findById(id);
        model.addAttribute("itemForm", itemEntity.get());
        model.addAttribute("message", "削除しますか？");
        return "item/delete";
    }
    
 // --- 商品の削除実行 ---
    @PostMapping("/itemlists/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
    	// 1. 削除を実行
        itemService1.deleteById(id);
        
        // 2. 画面の入力欄（th:field）がエラーにならないよう、空のFormを入れ直す
        model.addAttribute("itemForm", new ItemForm());
        
        // 3. 完了メッセージをセット
        model.addAttribute("message", "削除しました。");
        
        // 4. そのまま削除画面を表示
        return "item/delete";
    }

//	@PostMapping("/create")
//	public String list_form(@Validated ItemForm itemForm, BindingResult bindingResult, Model model) {
//		if (bindingResult.hasErrors()) {
//			return "item/create";
//		}
//		itemrepository.saveAndFlush(itemForm);
//		itemForm.clear();
//		model.addAttribute("message", "商品を登録しました。");
//		return "item/create";
//	}
    
 // --- 新規商品の登録処理 ---
    @PostMapping("/create")
    public String list_form(@Validated ItemForm itemForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return "item/create";

        itemService1.save(itemForm); 
        
        itemForm.clear();
        model.addAttribute("message", "商品を登録しました。");
        return "item/create";
    }
    
//	@PostMapping("/itemlists/{id}/edit")
//    public String update(@Validated ItemForm itemForm, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//			return "item/edit";
//		}
//	itemrepository.saveAndFlush(itemForm);
//	    itemForm.clear();
//	    model.addAttribute("message", "更新しました。");
//		return "item/edit";
//	}
	
 // --- 新規情報の登録処理 ---
	@PostMapping("/itemlists/{id}/edit")
    public String update(@Validated ItemForm itemForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
			return "item/edit";
		}
	itemService1.save(itemForm); 
	
	    itemForm.clear();
	    model.addAttribute("message", "更新しました。");
		return "item/edit";
	}
	
//	@PostMapping("/itemlists/{id}/delete")
//	public String delete(@PathVariable Long id, ItemForm itemForm, Model model) {
//		itemrepository.delete(itemForm);
//		itemForm.clear();
//		model.addAttribute("message", "削除しました。");
//		return "item/delete";
//	}

//	 @PutMapping("itemlists/{id}/")
//	 //戻り値Stringのupdateメソッドの定義(@PathVariable Long id, @ModelAttribute ItemForm itemForm)のパラメーターをセット
//	 //メソッドの引数に@PathVariableを設定するとURL上の値を取得することができる
//     //メソッドの引数に@ModelAttributeをつけると送信されたリクエストのbodyの情報を取得できる
//	    public String update(@PathVariable Long id, @ModelAttribute ItemForm itemForm) {
//	        itemForm.setId(id);
//	        itemrepository.saveAndFlush(itemForm);
//	        return "redirect:";
//	    }

//	    @DeleteMapping("itemlists/{id}")
//     //戻り値Stringのdestroyメソッドの定義(@PathVariable Long id)のパラメーターをセット
//	   //メソッドの引数に@PathVariableを設定するとURL上の値を取得することができる
//	    public String destroy(@PathVariable Long id) {
//	        itemrepository.delete(id);
//	        return "redirect:/itemlists";
//	    }
}