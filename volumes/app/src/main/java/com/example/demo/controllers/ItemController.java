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
import com.example.demo.repositries.InquiryRepository;
import com.example.demo.repositries.InquiryRepository3;
import com.example.demo.repositries.ItemRepository;

//import antlr.collections.List;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	//InquiryRepository repository = new InquiryRepository()
	@Autowired//アノテーション
	InquiryRepository repository;
	//インポートしないと定義されない
	@Autowired
	InquiryRepository3 repository3;

	@Autowired
	ItemRepository itemrepository;


	@GetMapping("/create")
	public String create(ItemForm itemForm) {
		return "item/create";
	}
	@GetMapping("/list")
	public String list(Model model) {
        List<ItemForm> itemlists = itemrepository.findAll();
        model.addAttribute("itemlists", itemlists);
		return "item/list";
	}
    @GetMapping("itemlists/{id}/edit")
  public String edit(@PathVariable Long id, Model model) { 
    ItemForm itemForm = itemrepository.findById(id).get();
      model.addAttribute("itemForm", itemForm);
        return "item/edit";
    }
    @GetMapping("itemlists/{id}/delete")
   public String delete(@PathVariable Long id, Model model) { 
    ItemForm itemForm = itemrepository.findById(id).get();
       model.addAttribute("itemForm", itemForm);
       model.addAttribute("message", "削除しますか？");
        return "item/delete";
    }


	@PostMapping("/create")
	public String list_form(@Validated ItemForm itemForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "item/create";
		}
		itemrepository.saveAndFlush(itemForm);
		itemForm.clear();
		model.addAttribute("message", "商品を登録しました。");
		return "item/create";
	}
	@PostMapping("/itemlists/{id}/edit")
    public String update(@Validated ItemForm itemForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
			return "item/edit";
		}
	itemrepository.saveAndFlush(itemForm);
	    itemForm.clear();
	    model.addAttribute("message", "更新しました。");
		return "item/edit";
	}
	@PostMapping("/itemlists/{id}/delete")
	public String delete(@PathVariable Long id, ItemForm itemForm, Model model) {
		itemrepository.delete(itemForm);
		itemForm.clear();
		model.addAttribute("message", "削除しました。");
		return "item/delete";
	}

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