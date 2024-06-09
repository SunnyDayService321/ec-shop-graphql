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
public class RootController {
	
	//InquiryRepository repository = new InquiryRepository()
	@Autowired//アノテーション
	InquiryRepository repository;
	//インポートしないと定義されない
	@Autowired
	InquiryRepository3 repository3;

	@Autowired
	ItemRepository itemrepository;

	@GetMapping
	public String index() {
		return "root/index";
	}
	//リクエストを受け取ってroot/formを返す
	@GetMapping("/form")
	public String form(InquiryForm inquiryForm) {
		return "root/form";
	}

	@GetMapping("/form2")
	public String form2(InquiryForm inquiryForm) {
		return "root/form2";
	}
	@GetMapping("/form3")
	public String form3(InquiryForm3 inquiryForm3) {
		return "root/form3";
	}
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

	@PostMapping("/form")
	public String form(@Validated InquiryForm inquiryForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "root/form";
		}
		String aaa;
		aaa = "アイウエオ";
		//送信ボタンを押すとPostMappingの/formにとんでrepository(SQL)に入ってsaveAndFlushでデータをセーブ
		// RDBと連携できることを確認しておきます。
		repository.saveAndFlush(inquiryForm);
		inquiryForm.clear();
		model.addAttribute("message", "お問い合わせを受け付けました。");
		return "root/form";
	}

	@PostMapping("/form2")
	public String form2(@Validated InquiryForm inquiryForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "root/form2";
		}
		repository.saveAndFlush(inquiryForm);
		inquiryForm.clear();
		model.addAttribute("message", "お問い合わせを受け付けました。");
		return "root/form";
	}
	@PostMapping("/form3")
	public String form3(@Validated InquiryForm3 inquiryForm3, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "root/form3";
		}
		repository3.saveAndFlush(inquiryForm3);
		inquiryForm3.clear();
		model.addAttribute("message", "お問い合わせを受け付けました。");
		return "root/form3";
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