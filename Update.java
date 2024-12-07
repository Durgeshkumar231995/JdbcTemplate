public void updateDepartment(Integer id, DepartmentDTO newDeptDetails) {
    Department existing = departmentRepository.findById(id);
    if (existing == null) {
        throw new ResourceNotFoundException("Department not found");
    }

    Department updated = new Department();
    updated.setId(id);
    updated.setEmail(Optional.ofNullable(newDeptDetails.getEmail()).orElse(existing.getEmail()));
    updated.setName(Optional.ofNullable(newDeptDetails.getName()).orElse(existing.getName()));
    // Repeat for other fields
    departmentRepository.update(updated);
}
