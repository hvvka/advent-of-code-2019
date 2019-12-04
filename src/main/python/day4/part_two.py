from part_one import are_digits_increasing


def has_at_least_one_double(n):
    dict = {i: str(n).count(i) for i in set(str(n))}
    for k, v in dict.items():
        if v == 2:
            return True
    return False


def calculate_passwords(input_from, input_to):
    combinations = [i for i in range(input_from, input_to)]
    solutions = []
    for c in combinations:
        if has_at_least_one_double(c) and are_digits_increasing(c):
            solutions.append(c)
    return solutions


if __name__ == '__main__':
    solutions = calculate_passwords(356261, 846303)
    print(len(solutions))  # 334
