package ir.maktabsharif.finalproject.controller.adminControl;

import ir.maktabsharif.finalproject.service.Impl.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("specification")
public class SpecificationController {
    private final SpecificationService specificationService;

    @Autowired
    public SpecificationController(SpecificationService specificationService) {
        this.specificationService = specificationService;
    }
    @GetMapping
    public String ShowSearchPage(){
        return "admin/specificSearch";
    }
}
