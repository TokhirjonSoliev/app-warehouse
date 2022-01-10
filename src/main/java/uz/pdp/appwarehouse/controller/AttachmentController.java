package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.AttachmentService;

import java.util.List;

@RestController
@RequestMapping(value = "/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @PostMapping
    public Result upload(MultipartHttpServletRequest request) {
        Result result = attachmentService.uploadFile(request);
        return result;
    }

    @GetMapping
    public List<Attachment> getAttachmentList() {
        return attachmentService.getAttachmentList();
    }

    @GetMapping(value = "/{attachmentId}")
    public Attachment getAttachmentController(@PathVariable Integer attachmentId) {
        return attachmentService.getAttachment(attachmentId);
    }

    @PutMapping(value = "/{attachmentId}")
    public Result editAttachmentController(@PathVariable Integer attachmentId, MultipartHttpServletRequest request) {
        return attachmentService.editAttachment(attachmentId, request);
    }

    @DeleteMapping(value = "/{attachmentId}")
    public Result deleteAttachmentController(@PathVariable Integer attachmentId) {
        return attachmentService.deleteAttachment(attachmentId);
    }
}
